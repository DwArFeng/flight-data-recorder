package com.dwarfeng.fdr.stack.exception;

/**
 * 过滤器构造异常。
 *
 * @author DwArFeng
 * @since 1.1.0.a
 */
public class FilterMakeException extends FilterException {

    private static final long serialVersionUID = 5584249920639455084L;

    public FilterMakeException() {
    }

    public FilterMakeException(String message) {
        super(message);
    }

    public FilterMakeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilterMakeException(Throwable cause) {
        super(cause);
    }
}
