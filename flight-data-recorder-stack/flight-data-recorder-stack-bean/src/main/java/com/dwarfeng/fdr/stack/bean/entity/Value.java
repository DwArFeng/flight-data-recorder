package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.UuidKey;

import java.util.Date;

/**
 * 值。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface Value extends Entity<UuidKey> {

    /**
     * 获取历史值的发生日期。
     *
     * @return 历史值的发生日期。
     */
    Date getHappenedDate();

    /**
     * 获取历史值的值。
     *
     * @return 历史值的值。
     */
    String getValue();

}
