package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.exception.TriggerException;

/**
 * 触发器处理器。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggerHandler extends Handler {

    /**
     * 根据指定的内容文本生成一个触发器。
     *
     * @param pointUuid   数据点的UUID。
     * @param triggerUuid 触发器的UUID。
     * @param content     指定的文本。
     * @return 通过指定的文本生成的触发器。
     * @throws TriggerException 触发器生成异常。
     */
    Trigger make(String pointUuid, String triggerUuid, String content) throws TriggerException;
}
