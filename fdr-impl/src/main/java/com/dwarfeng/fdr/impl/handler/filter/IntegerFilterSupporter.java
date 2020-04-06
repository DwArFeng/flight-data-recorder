package com.dwarfeng.fdr.impl.handler.filter;

import com.dwarfeng.fdr.impl.handler.FilterSupporter;
import org.springframework.stereotype.Component;

/**
 * Integer过滤器支持器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Component
public class IntegerFilterSupporter implements FilterSupporter {

    public static final String SUPPORT_TYPE = "integer_filter";

    @Override
    public String provideType() {
        return SUPPORT_TYPE;
    }

    @Override
    public String provideLabel() {
        return "整型数过滤器";
    }

    @Override
    public String provideDescription() {
        return "如果数据值是整型数，则通过过滤。";
    }

    @Override
    public String provideExampleContent() {
        return "";
    }
}
