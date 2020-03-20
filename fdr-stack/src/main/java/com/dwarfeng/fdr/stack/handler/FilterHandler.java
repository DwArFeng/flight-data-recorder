package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 过滤器处理器。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface FilterHandler extends Handler {

    /**
     * 通过指定的过滤器信息构造一个过滤器。
     *
     * @param filterInfo 指定的过滤器信息。
     * @return 构造的过滤器。
     * @throws HandlerException 处理器异常。
     */
    Filter make(FilterInfo filterInfo) throws HandlerException;
}
