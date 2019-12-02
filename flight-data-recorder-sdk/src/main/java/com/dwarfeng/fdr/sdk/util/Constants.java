package com.dwarfeng.fdr.sdk.util;

/**
 * 常量类。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public final class Constants {

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
    public static final int CONSTRAINT_LENGTH_CATAGORY_NAME = 50;
    /**
     * 分类备注的长度约束。
     */
    public static final int CONSTRAINT_LENGTH_CATAGORY_REMARK = 100;
    /**
     * 数据点名称的长度约束。
     */
    public static final int CONSTRAINT_LENGTH_POINT_NAME = 50;
    /**
     * 数据点备注的长度约束。
     */
    public static final int CONSTRAINT_LENGTH_POINT_REMARK = 100;
    /**
     * 数据点备注的长度约束。
     */
    public static final int CONSTRAINT_LENGTH_FILTER_INFO_REMARK = 100;

    /**
     * 批量缓存抓取的数据长度。
     */
    public static final int BATCH_CACHE_FETCH_SIZE = 100;


    private Constants() {
        throw new IllegalStateException("禁止实例化");
    }
}
