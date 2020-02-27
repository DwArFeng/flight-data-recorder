package com.dwarfeng.fdr.impl.handler;

import com.alibaba.fastjson.JSONObject;
import com.dwarfeng.fdr.impl.handler.struct.StructuredTrigger;
import com.dwarfeng.fdr.impl.handler.struct.StructuredTriggerInfo;
import com.dwarfeng.fdr.stack.exception.TriggerException;
import com.dwarfeng.fdr.stack.handler.Trigger;
import com.dwarfeng.fdr.stack.handler.TriggerHandler;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class TriggerHandlerImpl implements TriggerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggerHandlerImpl.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    @BehaviorAnalyse
    public Trigger make(@NotNull long pointGuid, @NotNull long triggerGuid, @NotNull String content) throws TriggerException {
        LOGGER.debug("1. 将content以JSON方式转化为StructuredTriggerInfo对象...");
        StructuredTriggerInfo structuredTriggerInfo;
        try {
            structuredTriggerInfo = JSONObject.parseObject(content, StructuredTriggerInfo.class);
        } catch (Exception e) {
            LOGGER.warn("过滤器信息中的内容 " + content + " 无法被转换成 StructuredTriggerInfo 对象，异常信息如下", e);
            throw new TriggerException("过滤器信息中的内容 " + content + " 无法被转换成 StructuredTriggerInfo 对象");
        }

        LOGGER.debug("2. 根据StructuredTriggerInfo提供的BeanId获取StructuredTrigger...");
        StructuredTrigger structuredTrigger;
        try {
            structuredTrigger = applicationContext.getBean(structuredTriggerInfo.getBeanId(), StructuredTrigger.class);
        } catch (Exception e) {
            LOGGER.warn("无法根据BeanId " + structuredTriggerInfo.getBeanId() + " 生成 StructuredTrigger 对象，将抛出异常...", e);
            throw new TriggerException("无法根据BeanId " + structuredTriggerInfo.getBeanId() + " 生成 StructuredTrigger 对象");
        }

        LOGGER.debug("3. StructuredTrigger应用配置信息...");
        structuredTrigger.applyPointGuid(pointGuid);
        structuredTrigger.applyTriggerGuid(triggerGuid);
        structuredTrigger.applyConfig(structuredTriggerInfo.getConfig());

        return structuredTrigger;
    }
}
