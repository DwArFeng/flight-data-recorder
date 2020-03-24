package com.dwarfeng.fdr.stack.exception;

/**
 * 不支持的映射器类型。
 *
 * @author DwArFeng
 * @since 1.4.1
 */
public class UnsupportedMapperTypeException extends MapperException {

    private static final long serialVersionUID = 978303303723374621L;

    private final String type;

    public UnsupportedMapperTypeException(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return "不支持的映射器类型: " + type;
    }
}
