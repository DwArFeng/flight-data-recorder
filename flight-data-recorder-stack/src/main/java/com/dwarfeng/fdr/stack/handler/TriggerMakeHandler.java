package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.exception.TriggerMakeException;

/**
 * 触发器处理器。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggerMakeHandler {

    /**
     * 根据指定的内容文本生成一个触发器。
     *
     * @param content 指定的文本。
     * @return 通过指定的文本生成的触发器。
     * @throws TriggerMakeException 触发器生成异常。
     */
    Trigger make(String content) throws TriggerMakeException;
}
