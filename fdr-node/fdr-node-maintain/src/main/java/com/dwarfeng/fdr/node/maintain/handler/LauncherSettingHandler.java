package com.dwarfeng.fdr.node.maintain.handler;

import com.dwarfeng.subgrade.stack.handler.Handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LauncherSettingHandler implements Handler {

    @Value("${launcher.reset_filter_support}")
    private boolean resetFilterSupport;
    @Value("${launcher.reset_trigger_support}")
    private boolean resetTriggerSupport;

    public boolean isResetFilterSupport() {
        return resetFilterSupport;
    }

    public boolean isResetTriggerSupport() {
        return resetTriggerSupport;
    }
}
