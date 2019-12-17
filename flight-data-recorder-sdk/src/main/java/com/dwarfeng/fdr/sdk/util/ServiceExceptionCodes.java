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

    private ServiceExceptionCodes() {
        throw new IllegalStateException("禁止实例化");
    }
}
