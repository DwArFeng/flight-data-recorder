package com.dwarfeng.fdr.stack.exception;

public class TriggerMakeException extends Exception {

    private static final long serialVersionUID = 5584249920639455084L;

    public TriggerMakeException() {
    }

    public TriggerMakeException(String message) {
        super(message);
    }

    public TriggerMakeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TriggerMakeException(Throwable cause) {
        super(cause);
    }
}
