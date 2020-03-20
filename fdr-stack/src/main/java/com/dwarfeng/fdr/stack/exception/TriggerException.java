package com.dwarfeng.fdr.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

public class TriggerException extends HandlerException {

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
