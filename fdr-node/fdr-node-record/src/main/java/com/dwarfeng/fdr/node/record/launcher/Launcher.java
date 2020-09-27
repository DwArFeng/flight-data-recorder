package com.dwarfeng.fdr.node.record.launcher;

import com.dwarfeng.fdr.node.record.handler.LauncherSettingHandler;
import com.dwarfeng.fdr.stack.service.RecordQosService;
import com.dwarfeng.springterminator.sdk.util.ApplicationUtil;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 程序启动器。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class Launcher {

    private final static Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        ApplicationUtil.launch(new String[]{
                "classpath:spring/application-context*.xml",
                "file:opt/opt*.xml",
                "file:optext/opt*.xml"
        }, ctx -> {
            LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);
            long startRecordDelay = launcherSettingHandler.getStartRecordDelay();
            RecordQosService recordQosService = ctx.getBean(RecordQosService.class);
            if (startRecordDelay == 0) {
                LOGGER.info("立即启动记录服务...");
                try {
                    recordQosService.startRecord();
                } catch (ServiceException e) {
                    LOGGER.error("无法启动记录服务，异常原因如下", e);
                }
            } else if (startRecordDelay > 0) {
                LOGGER.info(startRecordDelay + " 毫秒后启动记录服务...");
                try {
                    Thread.sleep(startRecordDelay);
                } catch (InterruptedException ignored) {
                }
                LOGGER.info("启动记录服务...");
                try {
                    recordQosService.startRecord();
                } catch (ServiceException e) {
                    LOGGER.error("无法启动记录服务，异常原因如下", e);
                }
            }
        });
    }
}
