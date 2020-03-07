package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.exception.TriggerException;
import com.dwarfeng.fdr.stack.handler.Trigger;

/**
 * 触发器制造器。
 *
 * @author DwArFeng
 * @since 1.1.0.a
 */
public interface TriggerMaker {

    /**
     * 返回制造器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 制造器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 根据指定的触发器信息生成一个触发器对象。
     * <p>可以保证传入的触发器信息中的类型是支持的。</p>
     *
     * @param triggerInfo 指定的触发器信息。
     * @return 制造出的触发器。
     * @throws TriggerException 触发器异常。
     */
    Trigger makeTrigger(TriggerInfo triggerInfo) throws TriggerException;

    /**
     * 提供类型。
     *
     * @return 类型。
     */
    String provideType();

    /**
     * 提供标签。
     *
     * @return 标签。
     */
    String provideLabel();

    /**
     * 提供描述。
     *
     * @return 描述。
     */
    String provideDescription();

    /**
     * 提供示例内容。
     *
     * @return 示例内容。
     */
    String provideExampleContent();
}
