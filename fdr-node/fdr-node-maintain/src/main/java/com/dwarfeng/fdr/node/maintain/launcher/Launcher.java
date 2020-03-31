package com.dwarfeng.fdr.node.maintain.launcher;

import com.dwarfeng.fdr.node.maintain.handler.LauncherSettingHandler;
import com.dwarfeng.fdr.stack.service.FilterSupportMaintainService;
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
 * @since 0.0.1-alpha
 */
public class Launcher {

    private final static Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/application-context*.xml");
        ctx.registerShutdownHook();
        ctx.start();
        LauncherSettingHandler launcherSettingHandler = ctx.getBean(LauncherSettingHandler.class);
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
        Terminator terminator = ctx.getBean(Terminator.class);
        System.exit(terminator.getExitCode());
    }
}
