package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.entity.MapperSupport;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson映射器支持。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class FastJsonMapperSupport implements Bean {

    private static final long serialVersionUID = -7046798288553307802L;

    public static FastJsonMapperSupport of(MapperSupport mapperSupport) {
        if (Objects.isNull(mapperSupport)) {
            return null;
        }
        return new FastJsonMapperSupport(
                FastJsonStringIdKey.of(mapperSupport.getKey()),
                mapperSupport.getLabel(),
                mapperSupport.getDescription(),
                mapperSupport.getArgsIllustrate()
        );
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "label", ordinal = 2)
    private String label;

    @JSONField(name = "description", ordinal = 3)
    private String description;

    @JSONField(name = "args_illustrate", ordinal = 4)
    private String argsIllustrate;

    public FastJsonMapperSupport() {
    }

    public FastJsonMapperSupport(FastJsonStringIdKey key, String label, String description, String argsIllustrate) {
        this.key = key;
        this.label = label;
        this.description = description;
        this.argsIllustrate = argsIllustrate;
    }

    public FastJsonStringIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonStringIdKey key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArgsIllustrate() {
        return argsIllustrate;
    }

    public void setArgsIllustrate(String argsIllustrate) {
        this.argsIllustrate = argsIllustrate;
    }

    @Override
    public String toString() {
        return "FastJsonMapperSupport{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", argsIllustrate='" + argsIllustrate + '\'' +
                '}';
    }
}
