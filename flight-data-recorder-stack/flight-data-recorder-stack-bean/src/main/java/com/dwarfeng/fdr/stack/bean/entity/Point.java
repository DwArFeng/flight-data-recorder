package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.UuidKey;

/**
 * 数据点接口。
 */
public interface Point extends Entity<UuidKey> {

    /**
     * 数据点的名称。
     *
     * @return 数据点的名称。
     */
    String getName();

    /**
     * 获取数据点的类型。
     *
     * @return 数据点的类型。
     */
    String getType();

    /**
     * 数据点是否持久化。
     *
     * @return 数据点是否持久化记录数据。
     */
    boolean isPersistence();

    /**
     * 获取数据点的备注。
     *
     * @return 数据点的备注。
     */
    String getRemark();

}
