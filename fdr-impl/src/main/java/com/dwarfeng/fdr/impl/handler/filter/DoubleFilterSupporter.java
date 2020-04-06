package com.dwarfeng.fdr.impl.handler.filter;

import com.dwarfeng.fdr.impl.handler.FilterSupporter;
import org.springframework.stereotype.Component;

/**
 * Double过滤器支持器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Component
public class DoubleFilterSupporter implements FilterSupporter {

    public static final String SUPPORT_TYPE = "double_filter";

    @Override
    public String provideType() {
        return SUPPORT_TYPE;
    }

    @Override
    public String provideLabel() {
        return "双精度浮点过滤器";
    }

    @Override
    public String provideDescription() {
        return "如果数据值是双精度浮点数，则通过过滤。";
    }

    @Override
    public String provideExampleContent() {
        return "";
    }
}
