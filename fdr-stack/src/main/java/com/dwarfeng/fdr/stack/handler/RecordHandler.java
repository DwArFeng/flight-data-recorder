package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 记录处理器。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public interface RecordHandler extends Handler {

    /**
     * 记录处理器是否被启用。
     *
     * @return 记录处理器是否被启用。
     * @throws HandlerException 处理器异常。
     */
    boolean isEnabled() throws HandlerException;

    /**
     * 启用记录处理器。
     *
     * @throws HandlerException 处理器异常。
     */
    void enable() throws HandlerException;

    /**
     * 禁用记录处理器。
     *
     * @throws HandlerException 处理器异常。
     */
    void disable() throws HandlerException;

    /**
     * 向程序中记录数据。
     *
     * @param message 文本形式的数据信息。
     * @throws HandlerException 处理器异常。
     */
    void record(String message) throws HandlerException;

    /**
     * 向程序中记录数据。
     *
     * @param dataInfo 指定的数据信息。
     * @throws HandlerException 处理器异常。
     */
    void record(DataInfo dataInfo) throws HandlerException;
}
