package com.dwarfeng.fdr.impl.handler.struct;

import com.alibaba.fastjson.annotation.JSONField;

import javax.validation.constraints.NotNull;

public class StructuredFilterInfo {

    @JSONField(name = "bean_id", ordinal = 1)
    @NotNull
    private String beanId;

    @JSONField(name = "config", ordinal = 2)
    @NotNull
    private Object config;

    public StructuredFilterInfo() {
    }

    public StructuredFilterInfo(@NotNull String beanId, @NotNull Object config) {
        this.beanId = beanId;
        this.config = config;
    }

    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    public Object getConfig() {
        return config;
    }

    public void setConfig(Object config) {
        this.config = config;
    }

    @Override
    public String toString() {
        return "StructuredFilterInfo{" +
                "beanId='" + beanId + '\'' +
                ", config=" + config +
                '}';
    }
}
