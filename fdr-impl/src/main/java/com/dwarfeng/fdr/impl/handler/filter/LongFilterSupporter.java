package com.dwarfeng.fdr.impl.handler.filter;

import com.dwarfeng.fdr.impl.handler.FilterSupporter;
import org.springframework.stereotype.Component;

/**
 * Long过滤器支持器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Component
public class LongFilterSupporter implements FilterSupporter {

    public static final String SUPPORT_TYPE = "long_filter";

    @Override
    public String provideType() {
        return SUPPORT_TYPE;
    }

    @Override
    public String provideLabel() {
        return "长整型浮点过滤器";
    }

    @Override
    public String provideDescription() {
        return "如果数据值是长整型数，则通过过滤。";
    }

    @Override
    public String provideExampleContent() {
        return "";
    }
}
