package com.dwarfeng.fdr.stack.bean.dto;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;

import java.util.List;

/**
 * 数据值。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class RecordResult implements Dto {

    private static final long serialVersionUID = 6360037498642277881L;

    private DataInfo dataInfo;
    private boolean filtered;
    private FilteredValue filteredValue;
    private boolean triggered;
    private List<TriggeredValue> triggeredValues;
    private boolean persistenceRecorded;
    private PersistenceValue persistenceValue;
    private boolean realtimeRecorded;
    private RealtimeValue realtimeValue;

    public RecordResult() {
    }

    public RecordResult(
            DataInfo dataInfo,
            boolean filtered,
            FilteredValue filteredValue,
            boolean triggered,
            List<TriggeredValue> triggeredValues,
            boolean persistenceRecorded,
            PersistenceValue persistenceValue,
            boolean realtimeRecorded,
            RealtimeValue realtimeValue
    ) {
        this.dataInfo = dataInfo;
        this.filtered = filtered;
        this.filteredValue = filteredValue;
        this.triggered = triggered;
        this.triggeredValues = triggeredValues;
        this.persistenceRecorded = persistenceRecorded;
        this.persistenceValue = persistenceValue;
        this.realtimeRecorded = realtimeRecorded;
        this.realtimeValue = realtimeValue;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public DataInfo getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(DataInfo dataInfo) {
        this.dataInfo = dataInfo;
    }

    public boolean isFiltered() {
        return filtered;
    }

    public void setFiltered(boolean filtered) {
        this.filtered = filtered;
    }

    public FilteredValue getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(FilteredValue filteredValue) {
        this.filteredValue = filteredValue;
    }

    public boolean isTriggered() {
        return triggered;
    }

    public void setTriggered(boolean triggered) {
        this.triggered = triggered;
    }

    public List<TriggeredValue> getTriggeredValues() {
        return triggeredValues;
    }

    public void setTriggeredValues(List<TriggeredValue> triggeredValues) {
        this.triggeredValues = triggeredValues;
    }

    public boolean isPersistenceRecorded() {
        return persistenceRecorded;
    }

    public void setPersistenceRecorded(boolean persistenceRecorded) {
        this.persistenceRecorded = persistenceRecorded;
    }

    public PersistenceValue getPersistenceValue() {
        return persistenceValue;
    }

    public void setPersistenceValue(PersistenceValue persistenceValue) {
        this.persistenceValue = persistenceValue;
    }

    public boolean isRealtimeRecorded() {
        return realtimeRecorded;
    }

    public void setRealtimeRecorded(boolean realtimeRecorded) {
        this.realtimeRecorded = realtimeRecorded;
    }

    public RealtimeValue getRealtimeValue() {
        return realtimeValue;
    }

    public void setRealtimeValue(RealtimeValue realtimeValue) {
        this.realtimeValue = realtimeValue;
    }

    @Override
    public String toString() {
        return "RecordResult{" +
                "dataInfo=" + dataInfo +
                ", filtered=" + filtered +
                ", filteredValue=" + filteredValue +
                ", triggered=" + triggered +
                ", triggeredValues=" + triggeredValues +
                ", persistenceRecorded=" + persistenceRecorded +
                ", persistenceValue=" + persistenceValue +
                ", realtimeRecorded=" + realtimeRecorded +
                ", realtimeValue=" + realtimeValue +
                '}';
    }
}
