package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.entity.TriggerSupport;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class WebInputTriggerSupport implements Bean {

    private static final long serialVersionUID = -3821348344682281897L;

    public static TriggerSupport toStackBean(WebInputTriggerSupport webInputTriggerSupport) {
        if (Objects.isNull(webInputTriggerSupport)) {
            return null;
        }
        return new TriggerSupport(
                WebInputStringIdKey.toStackBean(webInputTriggerSupport.getKey()),
                webInputTriggerSupport.getLabel(),
                webInputTriggerSupport.getDescription(),
                webInputTriggerSupport.getExampleContent()
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

    @JSONField(name = "example_content")
    private String exampleContent;

    public WebInputTriggerSupport() {
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

    public String getExampleContent() {
        return exampleContent;
    }

    public void setExampleContent(String exampleContent) {
        this.exampleContent = exampleContent;
    }

    @Override
    public String toString() {
        return "WebInputTriggerSupport{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", exampleContent='" + exampleContent + '\'' +
                '}';
    }
}
