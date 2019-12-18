package com.dwarfeng.fdr.stack.exception;

public class BroadcastException extends Exception {

    private static final long serialVersionUID = -3329832536659551685L;

    public BroadcastException() {
    }

    public BroadcastException(String message) {
        super(message);
    }

    public BroadcastException(String message, Throwable cause) {
        super(message, cause);
    }

    public BroadcastException(Throwable cause) {
        super(cause);
    }
}
