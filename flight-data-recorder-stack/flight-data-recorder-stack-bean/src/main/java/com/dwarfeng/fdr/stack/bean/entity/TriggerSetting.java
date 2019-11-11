package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * 触发器设置。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface TriggerSetting extends Entity<UuidKey> {

    /**
     * 获取触发器的名称。
     *
     * @return 触发器的名称。
     */
    String getName();

    /**
     * 获取触发器的数据。
     *
     * @return 触发器的数据。
     */
    String getTriggerData();

    /**
     * 获取触发器的备注。
     *
     * @return 触发器的备注。
     */
    String getRemark();

    /**
     * 描述数据点被触发时是否向外广播触发事件。
     *
     * @return 数据点被触发时是否向外广播触发事件。
     */
    boolean isTriggerBroadcast();

    /**
     * 描述数据点被触发时是否持久化触发信息。
     *
     * @return 数据点被触发时是否持久化触发信息。
     */
    boolean isTriggerPersistence();

}
