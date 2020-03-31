package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.dto.TimedValue;
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
     * 映射拥有发生时间的数据值。
     *
     * @param timedValues 映射前的拥有发生时间的数据值。
     * @return 映射后的拥有发生时间的数据值。
     * @throws MapperException 映射器异常。
     */
    List<TimedValue> map(List<TimedValue> timedValues) throws MapperException;
}
