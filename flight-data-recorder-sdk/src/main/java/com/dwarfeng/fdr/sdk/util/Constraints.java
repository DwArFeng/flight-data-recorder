package com.dwarfeng.fdr.sdk.util;

/**
 * 约束类。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public final class Constraints {

    /**
     * 一个紧缩的UUID的标准长度。
     */
    public static final int DENSE_UUID_LENGTH = 22;
    /**
     * 一个紧缩的UUID的正则表达式。
     */
    public static final String DENSE_UUID_REGEX = "^[a-zA-Z0-9/+]*={0,2}$";

    /**
     * 分类名称的长度约束。
     */
    public static final int LENGTH_CATAGORY_NAME = 50;
    /**
     * 分类备注的长度约束。
     */
    public static final int LENGTH_CATAGORY_REMARK = 100;
    /**
     * 数据点名称的长度约束。
     */
    public static final int LENGTH_POINT_NAME = 50;
    /**
     * 数据点备注的长度约束。
     */
    public static final int LENGTH_POINT_REMARK = 100;
    /**
     * 过滤器信息备注的长度约束。
     */
    public static final int LENGTH_FILTER_INFO_REMARK = 100;
    /**
     * 触发器信息备注的长度约束。
     */
    public static final int LENGTH_TRIGGER_INFO_REMARK = 100;
    /**
     * 被过滤数据值的长度约束。
     */
    public static final int LENGTH_FILTERED_VALUE_VALUE = 40;
    /**
     * 被过滤数据信息的长度约束。
     */
    public static final int LENGTH_FILTERED_VALUE_MESSAGE = 20;
    /**
     * 持久化数据值的长度约束。
     */
    public static final int LENGTH_PERSISTENCE_VALUE_VALUE = 40;
    /**
     * 实时数据值的长度约束。
     */
    public static final int LENGTH_REALTIME_VALUE_VALUE = 40;
    /**
     * 被触发数据值的长度约束。
     */
    public static final int LENGTH_TRIGGERED_VALUE_VALUE = 40;
    /**
     * 被触发数据信息的长度约束。
     */
    public static final int LENGTH_TRIGGERED_VALUE_MESSAGE = 20;

    private Constraints() {
        throw new IllegalStateException("禁止实例化");
    }
}
