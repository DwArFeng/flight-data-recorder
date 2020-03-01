package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.Date;

public class WebInputFilteredValue implements Bean {

    private static final long serialVersionUID = 2709175592898627872L;

    public static FilteredValue toStackBean(WebInputFilteredValue webInputFilteredValue) {
        return new FilteredValue(
                WebInputLongIdKey.toStackBean(webInputFilteredValue.getKey()),
                WebInputLongIdKey.toStackBean(webInputFilteredValue.getPointKey()),
                WebInputLongIdKey.toStackBean(webInputFilteredValue.getFilterKey()),
                webInputFilteredValue.getHappenedDate(),
                webInputFilteredValue.getValue(),
                webInputFilteredValue.getMessage()
        );
    }

    @JSONField(name = "key")
    @Valid
    @NotNull(groups = Default.class)
    private WebInputLongIdKey key;

    @JSONField(name = "point_key")
    @Valid
    private WebInputLongIdKey pointKey;

    @JSONField(name = "filter_key")
    @Valid
    private WebInputLongIdKey filterKey;

    @JSONField(name = "happened_date")
    @NotNull
    private Date happenedDate;

    @JSONField(name = "value")
    @NotNull
    private String value;

    @JSONField(name = "message")
    private String message;

    public WebInputFilteredValue() {
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

    public WebInputLongIdKey getFilterKey() {
        return filterKey;
    }

    public void setFilterKey(WebInputLongIdKey filterKey) {
        this.filterKey = filterKey;
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
        return "WebInputFilteredValue{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", filterKey=" + filterKey +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
