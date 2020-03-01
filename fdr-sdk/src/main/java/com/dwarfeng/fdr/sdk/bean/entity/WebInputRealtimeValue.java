package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class WebInputRealtimeValue implements Bean {

    private static final long serialVersionUID = 8965495903122490378L;

    public static RealtimeValue toStackBean(WebInputRealtimeValue webInputRealtimeValue) {
        return new RealtimeValue(
                WebInputLongIdKey.toStackBean(webInputRealtimeValue.getKey()),
                webInputRealtimeValue.getHappenedDate(),
                webInputRealtimeValue.getValue()
        );
    }

    @JSONField(name = "key")
    @NotNull
    @Valid
    private WebInputLongIdKey key;

    @JSONField(name = "happened_date")
    @NotNull
    private Date happenedDate;

    @JSONField(name = "value")
    @NotNull
    private String value;

    public WebInputRealtimeValue() {
    }

    public WebInputLongIdKey getKey() {
        return key;
    }

    public void setKey(WebInputLongIdKey key) {
        this.key = key;
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

    @Override
    public String toString() {
        return "WebInputRealtimeValue{" +
                "key=" + key +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                '}';
    }
}
