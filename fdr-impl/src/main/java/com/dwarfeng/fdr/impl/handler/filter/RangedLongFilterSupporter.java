package com.dwarfeng.fdr.impl.handler.filter;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.fdr.impl.handler.FilterSupporter;
import org.springframework.stereotype.Component;

/**
 * 具有范围的 Long过滤器支持器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Component
public class RangedLongFilterSupporter implements FilterSupporter {

    public static final String SUPPORT_TYPE = "ranged_long_filter";

    @Override
    public String provideType() {
        return SUPPORT_TYPE;
    }

    @Override
    public String provideLabel() {
        return "具有范围的长整型过滤器";
    }

    @Override
    public String provideDescription() {
        return "如果数据值是长整型数且数值在配置的范围之内，则通过过滤。";
    }

    @Override
    public String provideExampleContent() {
        return JSON.toJSONString(new RangedLongFilterMaker.Config(
                1L,
                true,
                -2L,
                false
        ), true);
    }
}
