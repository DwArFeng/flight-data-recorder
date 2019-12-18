package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.exception.FilterMakeException;

/**
 * 过滤器处理器。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface FilterMakeHandler {

    /**
     * 根据指定的内容文本生成一个过滤器。
     *
     * @param content 指定的文本。
     * @return 通过指定的文本生成的过滤器。
     * @throws FilterMakeException 过滤器生成异常。
     */
    Filter make(String content) throws FilterMakeException;
}
