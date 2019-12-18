package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.exception.EventException;

/**
 * 广播处理器。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface EventHandler extends Handler {

    /**
     * 数据被触发时执行的广播操作。
     *
     * @param triggeredValue 指定的数据被触发数据点。
     * @throws EventException 广播异常。
     */
    void fireDataTriggered(TriggeredValue triggeredValue) throws EventException;
}
