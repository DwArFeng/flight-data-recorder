package com.dwarfeng.fdr.stack.exception;

/**
 * 不支持的触发器类型。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class UnsupportedTriggerTypeException extends TriggerException {

    private static final long serialVersionUID = -6422017103933897109L;

    private final String type;

    public UnsupportedTriggerTypeException(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return "不支持的触发器类型: " + type;
    }
}
