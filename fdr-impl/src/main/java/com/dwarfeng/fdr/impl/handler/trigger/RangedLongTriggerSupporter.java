package com.dwarfeng.fdr.impl.handler.trigger;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.fdr.impl.handler.TriggerSupporter;
import org.springframework.stereotype.Component;

/**
 * 具有范围的 Long过滤器支持器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Component
public class RangedLongTriggerSupporter implements TriggerSupporter {

    public static final String SUPPORT_TYPE = "ranged_long_trigger";

    @Override
    public String provideType() {
        return SUPPORT_TYPE;
    }

    @Override
    public String provideLabel() {
        return "具有范围的长整型触发器";
    }

    @Override
    public String provideDescription() {
        return "如果数据值是长整型数且数值在配置的范围之内，则进行触发。";
    }

    @Override
    public String provideExampleContent() {
        return JSON.toJSONString(new RangedLongTriggerMaker.Config(
                1L,
                true,
                -2L,
                false
        ), true);
    }
}
