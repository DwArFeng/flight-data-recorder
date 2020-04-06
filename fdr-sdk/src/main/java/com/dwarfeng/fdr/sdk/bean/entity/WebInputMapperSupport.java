package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.entity.MapperSupport;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class WebInputMapperSupport implements Bean {

    private static final long serialVersionUID = -1916579640871199170L;

    public static MapperSupport toStackBean(WebInputMapperSupport webInputMapperSupport) {
        if (Objects.isNull(webInputMapperSupport)) {
            return null;
        }
        return new MapperSupport(
                WebInputStringIdKey.toStackBean(webInputMapperSupport.getKey()),
                webInputMapperSupport.getLabel(),
                webInputMapperSupport.getDescription(),
                webInputMapperSupport.getArgsIllustrate()
        );
    }

    @JSONField(name = "key")
    @Valid
    @NotNull
    private WebInputStringIdKey key;

    @JSONField(name = "label")
    @NotNull
    private String label;

    @JSONField(name = "description")
    private String description;

    @JSONField(name = "args_illustrate")
    private String argsIllustrate;

    public WebInputMapperSupport() {
    }

    public WebInputStringIdKey getKey() {
        return key;
    }

    public void setKey(WebInputStringIdKey key) {
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
        return "WebInputMapperSupport{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", argsIllustrate='" + argsIllustrate + '\'' +
                '}';
    }
}
