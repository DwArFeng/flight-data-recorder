package com.dwarfeng.fdr.impl.handler;

import com.alibaba.fastjson.JSONObject;
import com.dwarfeng.fdr.impl.handler.struct.StructuredFilter;
import com.dwarfeng.fdr.impl.handler.struct.StructuredFilterInfo;
import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.handler.Filter;
import com.dwarfeng.fdr.stack.handler.FilterHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class FilterHandlerImpl implements FilterHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterHandlerImpl.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    @BehaviorAnalyse
    public Filter make(@NotNull long pointGuid, @NotNull long filterGuid, @NotNull String content) throws FilterException {
        LOGGER.debug("1. 将content以JSON方式转化为StructuredFilterInfo对象...");
        StructuredFilterInfo structuredFilterInfo;
        try {
            structuredFilterInfo = JSONObject.parseObject(content, StructuredFilterInfo.class);
        } catch (Exception e) {
            LOGGER.warn("过滤器信息中的内容 " + content + " 无法被转换成 StructuredFilterInfo 对象，异常信息如下", e);
            throw new FilterException("过滤器信息中的内容 " + content + " 无法被转换成 StructuredFilterInfo 对象");
        }

        LOGGER.debug("2. 根据StructuredFilterInfo提供的BeanId获取StructuredFilter...");
        StructuredFilter structuredFilter;
        try {
            structuredFilter = applicationContext.getBean(structuredFilterInfo.getBeanId(), StructuredFilter.class);
        } catch (Exception e) {
            LOGGER.warn("无法根据BeanId " + structuredFilterInfo.getBeanId() + " 生成 StructuredFilter 对象，将抛出异常...", e);
            throw new FilterException("无法根据BeanId " + structuredFilterInfo.getBeanId() + " 生成 StructuredFilter 对象");
        }

        LOGGER.debug("3. StructuredFilter应用配置信息...");
        structuredFilter.applyPointGuid(pointGuid);
        structuredFilter.applyFilterGuid(filterGuid);
        structuredFilter.applyConfig(structuredFilterInfo.getConfig());

        return structuredFilter;
    }
}
