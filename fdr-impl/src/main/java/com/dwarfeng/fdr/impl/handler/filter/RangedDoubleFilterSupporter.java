package com.dwarfeng.fdr.impl.handler.filter;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.fdr.impl.handler.FilterSupporter;
import org.springframework.stereotype.Component;

/**
 * 具有范围的 Double过滤器支持器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Component
public class RangedDoubleFilterSupporter implements FilterSupporter {

    public static final String SUPPORT_TYPE = "ranged_double_filter";

    @Override
    public String provideType() {
        return SUPPORT_TYPE;
    }

    @Override
    public String provideLabel() {
        return "具有范围的双精度浮点过滤器";
    }

    @Override
    public String provideDescription() {
        return "如果数据值是双精度浮点数且数值在配置的范围之内，则通过过滤。";
    }

    @Override
    public String provideExampleContent() {
        return JSON.toJSONString(new RangedDoubleFilterMaker.Config(
                0.5,
                true,
                -1.25,
                false
        ), true);
    }
}