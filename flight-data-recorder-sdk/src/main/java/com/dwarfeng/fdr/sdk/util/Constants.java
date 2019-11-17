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
     * 数据库中，分类名称的长度。
     */
    public static final int DATABASE_LENGTH_CATAGORY_NAME = 50;
    /**
     * 数据库中，分类备注的长度。
     */
    public static final int DATABASE_LENGTH_CATAGORY_REMARK = 100;

    private Constants() {
        throw new IllegalStateException("禁止实例化");
    }
}
