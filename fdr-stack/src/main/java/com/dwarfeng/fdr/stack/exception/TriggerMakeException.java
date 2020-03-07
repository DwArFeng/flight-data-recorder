package com.dwarfeng.fdr.stack.exception;

/**
 * 触发器构造异常。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class TriggerMakeException extends TriggerException {

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
