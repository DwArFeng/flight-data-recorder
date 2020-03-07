package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 广播处理器。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface EventHandler extends Handler {

    /**
     * 数据被过滤时执行的广播操作。
     *
     * @param filteredValue 指定的被过滤数据值。
     * @throws HandlerException 处理器异常。
     */
    void fireDataFiltered(FilteredValue filteredValue) throws HandlerException;

    /**
     * 数据被触发时执行的广播操作。
     *
     * @param triggeredValue 指定的数据被触发数据点。
     * @throws HandlerException 处理器异常。
     */
    void fireDataTriggered(TriggeredValue triggeredValue) throws HandlerException;

    /**
     * 实时数据更新时执行的广播操作。
     *
     * @param realtimeValue 指定的实时数据值。
     * @throws HandlerException 处理器异常。
     */
    void fireRealtimeUpdated(RealtimeValue realtimeValue) throws HandlerException;

    /**
     * 持久数据记录时执行的广播操作。
     *
     * @param persistenceValue 指定的持久数据值。
     * @throws HandlerException 处理器异常。
     */
    void firePersistenceRecorded(PersistenceValue persistenceValue) throws HandlerException;
}
