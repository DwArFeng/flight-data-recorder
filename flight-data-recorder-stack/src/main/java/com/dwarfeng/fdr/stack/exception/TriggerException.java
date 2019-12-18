package com.dwarfeng.fdr.stack.exception;

public class TriggerException extends Exception {

    private static final long serialVersionUID = 5584249920639455084L;

    public TriggerException() {
    }

    public TriggerException(String message) {
        super(message);
    }

    public TriggerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TriggerException(Throwable cause) {
        super(cause);
    }
}
