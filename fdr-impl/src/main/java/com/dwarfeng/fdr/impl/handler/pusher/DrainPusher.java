package com.dwarfeng.fdr.impl.handler.pusher;

import com.dwarfeng.fdr.impl.handler.Pusher;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 简单的丢弃掉所有信息的推送器。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
@Component
public class DrainPusher implements Pusher {

    public static final String SUPPORT_TYPE = "drain";

    @Override
    public boolean supportType(String type) {
        return Objects.equals(SUPPORT_TYPE, type);
    }

    @Override
    public void dataFiltered(FilteredValue filteredValue) {
    }

    @Override
    public void dataFiltered(List<FilteredValue> filteredValues) {
    }

    @Override
    public void dataTriggered(TriggeredValue triggeredValue) {
    }

    @Override
    public void dataTriggered(List<TriggeredValue> triggeredValues) {
    }

    @Override
    public void realtimeUpdated(RealtimeValue realtimeValue) {
    }

    @Override
    public void realtimeUpdated(List<RealtimeValue> realtimeValues) {
    }

    @Override
    public void persistenceRecorded(PersistenceValue persistenceValue) {
    }

    @Override
    public void persistenceRecorded(List<PersistenceValue> persistenceValues) {
    }
}
