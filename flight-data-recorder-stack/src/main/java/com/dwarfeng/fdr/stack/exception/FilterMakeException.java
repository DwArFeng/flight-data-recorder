package com.dwarfeng.fdr.stack.exception;

public class FilterMakeException extends Exception {

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
