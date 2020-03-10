package com.dwarfeng.fdr.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 本地资源异常。
 *
 * @author DwArFeng
 * @since 1.2.0.a
 */
public class LocalResourceException extends HandlerException {

    private static final long serialVersionUID = -3018186496024567853L;

    public LocalResourceException() {
    }

    public LocalResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public LocalResourceException(String message) {
        super(message);
    }

    public LocalResourceException(Throwable cause) {
        super(cause);
    }
}
