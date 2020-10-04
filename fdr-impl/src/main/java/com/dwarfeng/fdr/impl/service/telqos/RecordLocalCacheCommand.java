package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.handler.Filter;
import com.dwarfeng.fdr.stack.handler.RecordLocalCacheHandler.RecordContext;
import com.dwarfeng.fdr.stack.handler.Trigger;
import com.dwarfeng.fdr.stack.service.RecordQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class RecordLocalCacheCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordLocalCacheCommand.class);

    private static final String IDENTITY = "rlc";
    private static final String DESCRIPTION = "本地缓存操作";
    private static final String CMD_LINE_SYNTAX_C = "rlc -c";
    private static final String CMD_LINE_SYNTAX_P = "rlc -p point-id";
    private static final String CMD_LINE_SYNTAX = CMD_LINE_SYNTAX_C + System.lineSeparator() + CMD_LINE_SYNTAX_P;

    public RecordLocalCacheCommand() {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
    }

    @Autowired
    private RecordQosService recordQosService;

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder("c").optionalArg(true).hasArg(false).desc("清除缓存").build());
        list.add(Option.builder("p").optionalArg(true).hasArg(true).type(Number.class).argName("point-id")
                .desc("查看指定数据点的详细信息，如果本地缓存中不存在，则尝试抓取").build());
        return list;
    }

    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            Pair<String, Integer> pair = analyseCommand(cmd);
            if (pair.getRight() != 1) {
                context.sendMessage("下列选项必须且只能含有一个: -c -p");
                context.sendMessage(CMD_LINE_SYNTAX);
                return;
            }
            switch (pair.getLeft()) {
                case "c":
                    handleC(context, cmd);
                    break;
                case "p":
                    handleP(context, cmd);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void handleC(Context context, CommandLine cmd) throws Exception {
        recordQosService.clearLocalCache();
        context.sendMessage("缓存已清空");
    }

    private void handleP(Context context, CommandLine cmd) throws Exception {
        long pointId;
        try {
            pointId = ((Number) cmd.getParsedOptionValue("p")).longValue();
        } catch (ParseException e) {
            LOGGER.warn("解析命令选项时发生异常，异常信息如下", e);
            context.sendMessage("命令行格式错误，正确的格式为: " + CMD_LINE_SYNTAX_P);
            context.sendMessage("请留意选项 p 后接参数的类型应该是数字 ");
            return;
        }
        RecordContext recordContext = recordQosService.getRecordContext(new LongIdKey(pointId));
        if (Objects.isNull(recordContext)) {
            context.sendMessage("not exists!");
            return;
        }
        context.sendMessage(String.format("point: %s", recordContext.getPoint().toString()));
        context.sendMessage("filters:");
        int index = 0;
        for (Filter filter : recordContext.getFilters()) {
            context.sendMessage(String.format("  %-3d %s", ++index, filter.toString()));
        }
        context.sendMessage("triggers:");
        index = 0;
        for (Trigger trigger : recordContext.getTriggers()) {
            context.sendMessage(String.format("  %-3d %s", ++index, trigger.toString()));
        }
    }

    private Pair<String, Integer> analyseCommand(CommandLine cmd) {
        int i = 0;
        String subCmd = null;
        if (cmd.hasOption("c")) {
            i++;
            subCmd = "c";
        }
        if (cmd.hasOption("p")) {
            i++;
            subCmd = "p";
        }
        return Pair.of(subCmd, i);
    }
}
