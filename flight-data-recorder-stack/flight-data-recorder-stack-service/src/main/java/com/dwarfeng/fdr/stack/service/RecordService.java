package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.bean.key.UuidKey;

import java.util.Date;

/**
 * 数据记录服务。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface RecordService extends Service {

    /**
     * 向指定的数据点追加数据值。
     *
     * <p>
     * 数据发生的时间为系统的当前时间。
     *
     * @param pointKey 指定的数据点。
     * @param value    追加的数据值。
     */
    void appendValue(UuidKey pointKey, String value);

    /**
     * 向指定的数据点追加数据值。
     *
     * @param pointKey     指定的数据点。
     * @param value        追加的数据值。
     * @param happenedDate 数据发生的时间。
     */
    void appendValue(UuidKey pointKey, String value, Date happenedDate);

}
