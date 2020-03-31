package com.dwarfeng.fdr.node.all.launcher;

import com.dwarfeng.fdr.node.all.handler.LauncherSettingHandler;
import com.dwarfeng.fdr.stack.service.FilterSupportMaintainService;
import com.dwarfeng.fdr.stack.service.RecordQosService;
import com.dwarfeng.fdr.stack.service.TriggerSupportMaintainService;
import com.dwarfeng.springterminator.stack.handler.Terminator;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 程序启动器。
 *
 * @author DwArFeng
 * @since 1.4.2
 */
public class Launcher {

    private final static Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/application-context*.xml");
        ctx.registerShutdownHook();
        ctx.start();
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);
        //判断是否重置触发器和过滤器。
        if (launcherSettingHandler.isResetFilterSupport()) {
            LOGGER.info("重置过滤器支持...");
            FilterSupportMaintainService maintainService = ctx.getBean(FilterSupportMaintainService.class);
            try {
                maintainService.reset();
            } catch (ServiceException e) {
                LOGGER.warn("过滤器支持重置失败，异常信息如下", e);
            }
        }
        if (launcherSettingHandler.isResetTriggerSupport()) {
            LOGGER.info("重置触发器支持...");
            TriggerSupportMaintainService maintainService = ctx.getBean(TriggerSupportMaintainService.class);
            try {
                maintainService.reset();
            } catch (ServiceException e) {
                LOGGER.warn("触发器支持重置失败，异常信息如下", e);
            }
        }
        // 判断是否开启记录服务。
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
        Terminator terminator = ctx.getBean(Terminator.class);
        System.exit(terminator.getExitCode());
    }
}
