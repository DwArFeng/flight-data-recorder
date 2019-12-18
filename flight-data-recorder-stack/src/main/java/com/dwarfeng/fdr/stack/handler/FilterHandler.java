package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.exception.FilterException;

/**
 * 过滤器处理器。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface FilterHandler extends Handler {

    /**
     * 根据指定的内容文本生成一个过滤器。
     *
     * @param pointUuid  数据点的UUID。
     * @param filterUuid 过滤器的UUID。
     * @param content    指定的内容。
     * @return 通过指定的文本生成的过滤器。
     * @throws FilterException 过滤器生成异常。
     */
    Filter make(String pointUuid, String filterUuid, String content) throws FilterException;
}
