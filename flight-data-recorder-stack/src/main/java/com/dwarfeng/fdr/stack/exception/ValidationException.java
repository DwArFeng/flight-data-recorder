package com.dwarfeng.fdr.stack.exception;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class ValidationException extends Exception {

    private static final long serialVersionUID = 2868504174193141049L;

    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    protected ValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
