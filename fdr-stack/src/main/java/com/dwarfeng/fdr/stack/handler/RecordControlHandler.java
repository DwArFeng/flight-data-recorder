package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 记录控制处理器。
 *
 * @author DwArFeng
 * @since 1.2.1
 */
public interface RecordControlHandler extends Handler {

    /**
     * 上线记录控制器。
     *
     * @throws HandlerException 控制器异常。
     */
    void online() throws HandlerException;

    /**
     * 下线记录控制器。
     *
     * @throws HandlerException 控制器异常。
     */
    void offline() throws HandlerException;
}
