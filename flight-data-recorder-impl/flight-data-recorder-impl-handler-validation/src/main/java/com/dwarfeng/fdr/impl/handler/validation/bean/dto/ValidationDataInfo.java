package com.dwarfeng.fdr.impl.handler.validation.bean.dto;

import com.dwarfeng.fdr.sdk.util.Constraints;
import com.dwarfeng.fdr.sdk.validation.DenseUUID;
import com.dwarfeng.fdr.stack.bean.dto.Dto;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class ValidationDataInfo implements Dto {

    @DenseUUID
    private String pointUuid;

    @NotNull
    @Length(max = Constraints.LENGTH_VALUE)
    private String value;

    @NotNull
    private Date happenedDate;

    public ValidationDataInfo() {
    }

    public ValidationDataInfo(String pointUuid, String value, Date happenedDate) {
        this.pointUuid = pointUuid;
        this.value = value;
        this.happenedDate = happenedDate;
    }

    public String getPointUuid() {
        return pointUuid;
    }

    public void setPointUuid(String pointUuid) {
        this.pointUuid = pointUuid;
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
        return "DataInfo{" +
                "pointUuid='" + pointUuid + '\'' +
                ", value='" + value + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
