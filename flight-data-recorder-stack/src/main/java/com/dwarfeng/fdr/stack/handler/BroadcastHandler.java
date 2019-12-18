package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.exception.BroadcastException;

/**
 * 广播处理器。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface BroadcastHandler {

    /**
     * 数据被触发时执行的广播操作。
     *
     * @param dataInfo 指定的数据信息。
     * @throws BroadcastException 广播异常。
     */
    void broadcastDataTriggered(DataInfo dataInfo) throws BroadcastException;
}
