package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.service.RecordQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecordCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordCommand.class);

    private static final String IDENTITY = "record";
    private static final String DESCRIPTION = "记录功能上线/下线";
    private static final String CMD_LINE_SYNTAX = "record online|offline";

    public RecordCommand() {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
    }

    @Autowired
    private RecordQosService recordQosService;

    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            String option = cmd.getArgList().stream().findFirst().orElse("");
            switch (option) {
                case "online":
                    recordQosService.startRecord();
                    context.sendMessage("记录功能已上线!");
                    break;
                case "offline":
                    recordQosService.stopRecord();
                    context.sendMessage("记录功能已下线!");
                    break;
                default:
                    context.sendMessage("命令行格式错误，正确的格式为: " + CMD_LINE_SYNTAX);
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }
}
