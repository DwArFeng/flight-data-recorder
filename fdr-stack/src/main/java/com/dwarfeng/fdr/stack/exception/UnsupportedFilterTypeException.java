package com.dwarfeng.fdr.stack.exception;

/**
 * 不支持的过滤器类型。
 *
 * @author DwArFeng
 * @since 1.1.0.a
 */
public class UnsupportedFilterTypeException extends FilterException {

    private static final long serialVersionUID = -6422017103933897109L;

    private final String type;

    public UnsupportedFilterTypeException(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return " " + type;
    }
}
