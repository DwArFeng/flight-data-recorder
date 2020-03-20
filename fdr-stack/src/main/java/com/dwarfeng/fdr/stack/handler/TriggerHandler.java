package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 触发器处理器。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggerHandler extends Handler {

    /**
     * 通过指定的触发器信息构造一个触发器。
     *
     * @param filterInfo 指定的触发器信息。
     * @return 构造的触发器。
     * @throws HandlerException 处理器异常。
     */
    Trigger make(TriggerInfo filterInfo) throws HandlerException;
}
