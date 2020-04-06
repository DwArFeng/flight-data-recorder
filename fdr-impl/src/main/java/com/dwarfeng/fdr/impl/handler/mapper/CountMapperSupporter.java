package com.dwarfeng.fdr.impl.handler.mapper;

import com.dwarfeng.fdr.impl.handler.MapperSupporter;
import org.springframework.stereotype.Component;

/**
 * 计数映射器支持器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Component
public class CountMapperSupporter implements MapperSupporter {

    public static final String SUPPORT_TYPE = "count_mapper";

    @Override
    public String provideType() {
        return SUPPORT_TYPE;
    }

    @Override
    public String provideLabel() {
        return "计数映射器";
    }

    @Override
    public String provideDescription() {
        return "将输入的数据统计个数并输出，时间为统计行为发生的时间。";
    }

    @Override
    public String provideArgsIllustrate() {
        return "";
    }
}
