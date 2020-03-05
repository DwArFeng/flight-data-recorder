package com.dwarfeng.fdr.impl.fuhbean.entity;

import com.dwarfeng.fdr.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Objects;

@Entity
@IdClass(HibernateStringIdKey.class)
@Table(name = "tbl_filter_support")
public class HibernateFilterSupport implements Bean {

    private static final long serialVersionUID = -8833768341499733518L;
    
    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", length = Constraints.LENGTH_TYPE, nullable = false, unique = true)
    private String stringId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "label", length = 50, nullable = false)
    private String label;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "example_content", columnDefinition = "TEXT")
    private String exampleContent;

    public HibernateFilterSupport() {
    }

    public HibernateStringIdKey getKey() {
        if (Objects.isNull(stringId)) {
            return null;
        }
        return new HibernateStringIdKey(stringId);
    }

    public void setKey(HibernateStringIdKey key) {
        if (Objects.isNull(key)) {
            this.stringId = null;
        }
        this.stringId = key.getStringId();
    }

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
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
        return "HibernateFilterSupport{" +
                "stringId='" + stringId + '\'' +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", exampleContent='" + exampleContent + '\'' +
                '}';
    }
}
