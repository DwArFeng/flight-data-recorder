package com.dwarfeng.fdr.sdk.validation.bean.dto;

import com.dwarfeng.fdr.sdk.util.Constraints;
import com.dwarfeng.fdr.sdk.validation.group.Insert;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.groups.Default;
import java.io.Serializable;
import java.util.Date;

public class ValidationDataInfo implements Serializable {

    private static final long serialVersionUID = -6897798661959655112L;

    @Positive(groups = {Default.class, Insert.class})
    private long pointGuid;

    @NotNull(groups = {Default.class, Insert.class})
    @Length(max = Constraints.LENGTH_VALUE, groups = {Default.class, Insert.class})
    private String value;

    @NotNull(groups = {Default.class, Insert.class})
    private Date happenedDate;

    public ValidationDataInfo() {
    }

    public long getPointGuid() {
        return pointGuid;
    }

    public void setPointGuid(long pointGuid) {
        this.pointGuid = pointGuid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    @Override
    public String toString() {
        return "ValidationDataInfo{" +
                "pointGuid=" + pointGuid +
                ", value='" + value + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
