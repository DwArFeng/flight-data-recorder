package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.Date;

public class WebInputTriggeredValue implements Bean {

    private static final long serialVersionUID = 1097223583747510722L;

    public static TriggeredValue toStackBean(WebInputTriggeredValue webInputTriggeredValue) {
        return new TriggeredValue(
                WebInputLongIdKey.toStackBean(webInputTriggeredValue.getKey()),
                WebInputLongIdKey.toStackBean(webInputTriggeredValue.getPointKey()),
                WebInputLongIdKey.toStackBean(webInputTriggeredValue.getTriggerKey()),
                webInputTriggeredValue.getHappenedDate(),
                webInputTriggeredValue.getValue(),
                webInputTriggeredValue.getMessage()
        );
    }

    @JSONField(name = "key")
    @Valid
    @NotNull(groups = Default.class)
    private WebInputLongIdKey key;

    @JSONField(name = "point_key")
    @Valid
    private WebInputLongIdKey pointKey;

    @JSONField(name = "trigger_key")
    @Valid
    private WebInputLongIdKey triggerKey;

    @JSONField(name = "happened_date")
    @NotNull
    private Date happenedDate;

    @JSONField(name = "value")
    @NotNull
    private String value;

    @JSONField(name = "message")
    private String message;

    public WebInputTriggeredValue() {
    }

    public WebInputLongIdKey getKey() {
        return key;
    }

    public void setKey(WebInputLongIdKey key) {
        this.key = key;
    }

    public WebInputLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(WebInputLongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public WebInputLongIdKey getTriggerKey() {
        return triggerKey;
    }

    public void setTriggerKey(WebInputLongIdKey triggerKey) {
        this.triggerKey = triggerKey;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "WebInputTriggeredValue{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", triggerKey=" + triggerKey +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
