package com.dwarfeng.fdr.impl.handler.trigger;

import com.dwarfeng.fdr.impl.handler.TriggerSupporter;
import org.springframework.stereotype.Component;

/**
 * 使用Groovy脚本的触发器支持器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Component
public class GroovyTriggerSupporter implements TriggerSupporter {

    public static final String SUPPORT_TYPE = "groovy_trigger";

    @Override
    public String provideType() {
        return SUPPORT_TYPE;
    }

    @Override
    public String provideLabel() {
        return "Groovy触发器";
    }

    @Override
    public String provideDescription() {
        return "通过自定义的groovy脚本，判断数据点是否通过过滤";
    }

    @Override
    public String provideExampleContent() {
        return "";
    }
}
