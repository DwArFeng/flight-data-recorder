package com.dwarfeng.fdr.stack.exception;

public class MapperMakeException extends MapperException {

    private static final long serialVersionUID = 3952746793798276688L;

    public MapperMakeException() {
    }

    public MapperMakeException(String message) {
        super(message);
    }

    public MapperMakeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapperMakeException(Throwable cause) {
        super(cause);
    }
}
