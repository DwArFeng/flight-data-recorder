package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 消费者。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public interface Consumer<E extends Entity<?>> {

    /**
     * 消费指定的元素。
     *
     * @param elements 指定的元素组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void consume(List<E> elements) throws HandlerException;
}
