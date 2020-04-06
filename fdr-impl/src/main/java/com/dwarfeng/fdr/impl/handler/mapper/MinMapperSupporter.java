package com.dwarfeng.fdr.impl.handler.mapper;

import com.dwarfeng.fdr.impl.handler.MapperSupporter;
import org.springframework.stereotype.Component;

/**
 * 最小值映射器支持器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Component
public class MinMapperSupporter implements MapperSupporter {

    public static final String SUPPORT_TYPE = "min_mapper";

    @Override
    public String provideType() {
        return SUPPORT_TYPE;
    }

    @Override
    public String provideLabel() {
        return "最小值映射器";
    }

    @Override
    public String provideDescription() {
        return "统计输入数据中的最小数据并输出，要求数据的全部数据均必须能被解析为double。";
    }

    @Override
    public String provideArgsIllustrate() {
        return "";
    }
}
