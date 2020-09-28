package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.service.RecordQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.springterminator.stack.handler.Terminator;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ShutdownCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShutdownCommand.class);

    private static final String IDENTITY = "shutdown";
    private static final String DESCRIPTION = "列出指令";
    private static final String CMD_LINE_SYNTAX = "shutdown [-e exit-code] [-c comment]";

    public ShutdownCommand() {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private Terminator terminator;
    @Autowired(required = false)
    private RecordQosService recordQosService;

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder("e").optionalArg(true).type(Number.class).hasArg(true)
                .argName("exit-code").desc("退出代码").build());
        list.add(Option.builder("c").optionalArg(true).type(String.class).hasArg(true)
                .argName("comment").desc("备注").build());
        return list;
    }

    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            // 解析参数。
            int exitCode = 0;
            String comment = null;
            if (cmd.hasOption("e")) {
                exitCode = ((Number) cmd.getParsedOptionValue("e")).intValue();
            }
            if (cmd.hasOption("c")) {
                comment = (String) cmd.getParsedOptionValue("c");
            }
            // 二次确认。
            boolean confirmFlag;
            a:
            do {
                context.sendMessage("服务将会关闭，您可能需要登录远程主机才能重新启动该服务，是否继续? Y/N");
                String confirmMessage = context.receiveMessage();
                switch (confirmMessage.toUpperCase()) {
                    case "Y":
                        confirmFlag = true;
                        break a;
                    case "N":
                        confirmFlag = false;
                        break a;
                    default:
                        context.sendMessage("输入信息非法，请输入 Y 或者 N");
                        break;
                }
            } while (true);
            // 判断确认结果以及执行关闭动作。
            if (confirmFlag) {
                context.sendMessage("已确认请求，服务即将关闭...");
                if (StringUtils.isEmpty(comment)) {
                    LOGGER.warn("设备 {} 通过 QOS 系统关闭了该服务，退出代码设置为 {}，备注未填",
                            context.getAddress(), exitCode);
                } else {
                    LOGGER.warn("设备 {} 通过 QOS 系统关闭了该服务，退出代码设置为 {}，备注为 {}",
                            context.getAddress(), exitCode, comment);
                }
                context.quit();
                if (Objects.nonNull(recordQosService)) {
                    recordQosService.stopRecord();
                    recordQosService.clearLocalCache();
                }
                terminator.exit();
            } else {
                context.sendMessage("已确认请求，服务不会不关闭...");
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }
}
