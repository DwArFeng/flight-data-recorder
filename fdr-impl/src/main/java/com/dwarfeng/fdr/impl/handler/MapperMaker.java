package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.exception.MapperException;
import com.dwarfeng.fdr.stack.handler.Mapper;

/**
 * 映射器制造器。
 *
 * @author DwArFeng
 * @since 1.4.1
 */
public interface MapperMaker {

    /**
     * 映射器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 构造映射器。
     *
     * @param args 参数。
     * @return 构造的映射器。
     * @throws MapperException 映射器异常。
     */
    Mapper makeMapper(Object[] args) throws MapperException;
}
