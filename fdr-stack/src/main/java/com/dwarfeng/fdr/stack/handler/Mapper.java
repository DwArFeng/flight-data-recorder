package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.dto.MappedValue;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.exception.MapperException;

import java.util.List;

/**
 * 映射器。
 *
 * @author DwArFeng
 * @since 1.4.1
 */
public interface Mapper {

    /**
     * 映射器是否支持持久化数据值。
     *
     * @return 是否支持。
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean supportPersistenceValue();

    /**
     * 映射器是否支持被过滤数据值。
     *
     * @return 是否支持。
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean supportFilteredValue();

    /**
     * 映射器是否支持被触发数据值。
     *
     * @return 是否支持。
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean supportTriggeredValue();

    /**
     * 映射持久化数据值。
     *
     * @param persistenceValues 持久化数据值组成的列表。
     * @return 映射值。
     * @throws MapperException 映射器异常。
     */
    List<MappedValue> mapPersistenceValue(List<PersistenceValue> persistenceValues) throws MapperException;

    /**
     * 映射被过滤数据值。
     *
     * @param filteredValues 持久化被过滤组成的列表。
     * @return 映射值。
     * @throws MapperException 映射器异常。
     */
    List<MappedValue> mapFilteredValue(List<FilteredValue> filteredValues) throws MapperException;

    /**
     * 映射被触发数据值。
     *
     * @param triggeredValues 持久化被触发组成的列表。
     * @return 映射值。
     * @throws MapperException 映射器异常。
     */
    List<MappedValue> mapTriggeredValue(List<TriggeredValue> triggeredValues) throws MapperException;
}
