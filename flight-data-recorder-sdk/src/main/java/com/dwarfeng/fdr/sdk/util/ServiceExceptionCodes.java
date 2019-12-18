package com.dwarfeng.fdr.sdk.util;

import com.dwarfeng.fdr.stack.exception.ServiceException;

/**
 * 服务异常代码。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public final class ServiceExceptionCodes {

    /**
     * 未定义错误代码，代表未定义的错误。
     */
    public static final ServiceException.Code UNDEFINE = new ServiceException.Code(0, "undefine");
    /**
     * 参数验证失败。
     */
    public static final ServiceException.Code VALIDATION_FAILED = new ServiceException.Code(100, "validation failed");
    /**
     * 缓存异常。
     */
    public static final ServiceException.Code CACHE_FAILED = new ServiceException.Code(101, "cache failed");
    /**
     * 数据访问层异常。
     */
    public static final ServiceException.Code DAO_FAILED = new ServiceException.Code(101, "dao failed");
//    /**
//     * 过滤器构造失败。
//     */
//    public static final ServiceException.Code FILTER_MAKE_FAILED = new ServiceException.Code(102, "filter make failed");
//    /**
//     * 触发器构造失败。
//     */
//    public static final ServiceException.Code TRIGGER_MAKE_FAILED = new ServiceException.Code(103, "trigger make failed");

    private ServiceExceptionCodes() {
        throw new IllegalStateException("禁止实例化");
    }
}
