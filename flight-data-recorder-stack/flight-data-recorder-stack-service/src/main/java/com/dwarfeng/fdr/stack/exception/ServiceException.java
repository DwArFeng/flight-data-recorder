package com.dwarfeng.fdr.stack.exception;

/**
 * 服务端异常。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = 484985291925886736L;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
