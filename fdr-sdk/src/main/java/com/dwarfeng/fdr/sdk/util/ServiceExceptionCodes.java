package com.dwarfeng.fdr.sdk.util;

import com.dwarfeng.subgrade.stack.exception.ServiceException;

/**
 * 服务异常代码。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public final class ServiceExceptionCodes {

    private static int EXCEPTION_CODE_OFFSET = 5000;

    public static final ServiceException.Code FILTER_FAILED =
            new ServiceException.Code(EXCEPTION_CODE_OFFSET, "filter failed");
    public static final ServiceException.Code FILTER_MAKE_FAILED =
            new ServiceException.Code(EXCEPTION_CODE_OFFSET + 1, "filter make failed");
    public static final ServiceException.Code FILTER_TYPE_UNSUPPORTED =
            new ServiceException.Code(EXCEPTION_CODE_OFFSET + 2, "filter type unsupported");
    public static final ServiceException.Code TRIGGER_FAILED =
            new ServiceException.Code(EXCEPTION_CODE_OFFSET + 10, "trigger failed");
    public static final ServiceException.Code TRIGGER_MAKE_FAILED =
            new ServiceException.Code(EXCEPTION_CODE_OFFSET + 11, "trigger make failed");
    public static final ServiceException.Code TRIGGER_TYPE_UNSUPPORTED =
            new ServiceException.Code(EXCEPTION_CODE_OFFSET + 12, "trigger type unsupported");
    public static final ServiceException.Code POINT_NOT_EXISTS =
            new ServiceException.Code(EXCEPTION_CODE_OFFSET + 20, "point not exists");

    /**
     * 获取异常代号的偏移量。
     *
     * @return 异常代号的偏移量。
     */
    public static int getExceptionCodeOffset() {
        return EXCEPTION_CODE_OFFSET;
    }

    /**
     * 设置异常代号的偏移量。
     *
     * @param exceptionCodeOffset 指定的异常代号的偏移量。
     */
    public static void setExceptionCodeOffset(int exceptionCodeOffset) {
        EXCEPTION_CODE_OFFSET = exceptionCodeOffset;
    }

    private ServiceExceptionCodes() {
        throw new IllegalStateException("禁止实例化");
    }
}
