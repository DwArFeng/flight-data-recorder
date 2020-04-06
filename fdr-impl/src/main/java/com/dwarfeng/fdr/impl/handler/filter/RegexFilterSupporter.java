package com.dwarfeng.fdr.impl.handler.filter;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.fdr.impl.handler.FilterSupporter;
import org.springframework.stereotype.Component;

/**
 * 正则表达式过滤器制造器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Component
public class RegexFilterSupporter implements FilterSupporter {

    public static final String SUPPORT_TYPE = "regex_filter";

    @Override
    public String provideType() {
        return SUPPORT_TYPE;
    }

    @Override
    public String provideLabel() {
        return "正则表达式过滤器";
    }

    @Override
    public String provideDescription() {
        return "如果数据值匹配指定的正则表达式，则通过过滤。";
    }

    @Override
    public String provideExampleContent() {
        return JSON.toJSONString(new RegexFilterMaker.Config("^\\d+$"), true);
    }

}
