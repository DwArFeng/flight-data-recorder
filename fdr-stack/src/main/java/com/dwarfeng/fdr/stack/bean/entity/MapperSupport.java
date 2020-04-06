package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 映射器支持。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public class MapperSupport implements Entity<StringIdKey> {

    private static final long serialVersionUID = -3776716826749233066L;

    private StringIdKey key;
    private String label;
    private String description;
    private String argsIllustrate;

    public MapperSupport() {
    }

    public MapperSupport(StringIdKey key, String label, String description, String argsIllustrate) {
        this.key = key;
        this.label = label;
        this.description = description;
        this.argsIllustrate = argsIllustrate;
    }

    @Override
    public StringIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(StringIdKey key) {
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
        return "MapperSupport{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", argsIllustrate='" + argsIllustrate + '\'' +
                '}';
    }
}
