package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.service.RecordQosService;
import com.dwarfeng.fdr.stack.service.RecordQosService.RecorderStatus;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class RecorderCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecorderCommand.class);

    private static final String IDENTITY = "rec";
    private static final String DESCRIPTION = "记录者操作";
    private static final String CMD_LINE_SYNTAX_L = "rec -l";
    private static final String CMD_LINE_SYNTAX_S = "rec -s [-b val] [-t val]";
    private static final String CMD_LINE_SYNTAX = CMD_LINE_SYNTAX_L + System.lineSeparator() + CMD_LINE_SYNTAX_S;

    public RecorderCommand() {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
    }

    @Autowired
    private RecordQosService recordQosService;

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder("l").optionalArg(true).hasArg(false).desc("查看记录者状态").build());
        list.add(Option.builder("s").optionalArg(true).hasArg(false).desc("设置记录者参数").build());
        list.add(Option.builder("b").optionalArg(true).hasArg(true).type(Number.class)
                .argName("buffer-size").desc("缓冲器的大小").build());
        list.add(Option.builder("t").optionalArg(true).hasArg(true).type(Number.class)
                .argName("thread").desc("记录者的线程数量").build());
        return list;
    }

    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            Pair<String, Integer> pair = analyseCommand(cmd);
            if (pair.getRight() != 1) {
                context.sendMessage("下列选项必须且只能含有一个: -l -s");
                context.sendMessage(CMD_LINE_SYNTAX);
                return;
            }
            switch (pair.getLeft()) {
                case "l":
                    handleL(context);
                    break;
                case "s":
                    handleS(context, cmd);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void handleL(Context context) throws Exception {
        printRecorderStatus(context);
    }

    private void handleS(Context context, CommandLine cmd) throws Exception {
        Integer newBufferSize = null;
        Integer newThread = null;
        try {
            if (cmd.hasOption("b")) newBufferSize = Integer.parseInt(cmd.getOptionValue("b"));
            if (cmd.hasOption("t")) newThread = Integer.parseInt(cmd.getOptionValue("t"));
        } catch (Exception e) {
            LOGGER.warn("解析命令选项时发生异常，异常信息如下", e);
            context.sendMessage("命令行格式错误，正确的格式为: " + CMD_LINE_SYNTAX_S);
            context.sendMessage("请留意选项 b,t 后接参数的类型应该是数字 ");
            return;
        }

        RecorderStatus recorderStatus = recordQosService.getRecorderStatus();
        int bufferSize = Objects.nonNull(newBufferSize) ? newBufferSize : recorderStatus.getBufferSize();
        int thread = Objects.nonNull(newThread) ? newThread : recorderStatus.getThread();
        recordQosService.setRecorderParameters(bufferSize, thread);

        context.sendMessage("设置完成，记录者新的参数为: ");
        printRecorderStatus(context);
    }

    private void printRecorderStatus(Context context) throws ServiceException, TelqosException {
        RecorderStatus recorderStatus = recordQosService.getRecorderStatus();
        context.sendMessage(String.format("buffered-size:%-7d buffer-size:%-7d thread:%-3d idle:%b",
                recorderStatus.getBufferedSize(), recorderStatus.getBufferSize(), recorderStatus.getThread(),
                recorderStatus.isIdle())
        );
    }

    @SuppressWarnings("DuplicatedCode")
    private Pair<String, Integer> analyseCommand(CommandLine cmd) {
        int i = 0;
        String subCmd = null;
        if (cmd.hasOption("l")) {
            i++;
            subCmd = "l";
        }
        if (cmd.hasOption("s")) {
            i++;
            subCmd = "s";
        }
        return Pair.of(subCmd, i);
    }
}
