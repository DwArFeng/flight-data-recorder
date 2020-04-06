package com.dwarfeng.fdr.impl.handler.trigger;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.fdr.impl.handler.TriggerSupporter;
import org.springframework.stereotype.Component;

/**
 * 具有范围的 Double过滤器支持器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Component
public class RangedDoubleTriggerSupporter implements TriggerSupporter {

    public static final String SUPPORT_TYPE = "ranged_double_trigger";

    @Override
    public String provideType() {
        return SUPPORT_TYPE;
    }

    @Override
    public String provideLabel() {
        return "具有范围的双精度浮点触发器";
    }

    @Override
    public String provideDescription() {
        return "如果数据值是双精度浮点数且数值在配置的范围之内，则进行触发。";
    }

    @Override
    public String provideExampleContent() {
        return JSON.toJSONString(new RangedDoubleTriggerMaker.Config(
                0.5,
                true,
                -1.25,
                false
        ), true);
    }
}
