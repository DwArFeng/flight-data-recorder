package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 映射器处理器。
 *
 * @author DwArFeng
 * @since 1.4.1
 */
public interface MapperHandler extends Handler {

    /**
     * 构造映射器。
     *
     * @param type 映射器的类型。
     * @param args 映射器的参数。
     * @return 构造出的映射器。
     * @throws HandlerException 处理器异常。
     */
    Mapper make(String type, Object[] args) throws HandlerException;
}
