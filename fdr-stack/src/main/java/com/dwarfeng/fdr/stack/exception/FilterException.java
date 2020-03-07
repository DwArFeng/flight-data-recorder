package com.dwarfeng.fdr.stack.exception;

public class FilterException extends Exception {

    private static final long serialVersionUID = 5584249920639455084L;

    public FilterException() {
    }

    public FilterException(String message) {
        super(message);
    }

    public FilterException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilterException(Throwable cause) {
        super(cause);
    }
}
