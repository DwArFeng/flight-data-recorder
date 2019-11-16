package com.dwarfeng.fdr.impl.dao.hibernate.bean.entity;

import com.dwarfeng.fdr.impl.dao.hibernate.bean.key.HibernateUuidKey;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@IdClass(HibernateUuidKey.class)
@Table(name = "tbl_point")
public class HibernatePoint implements Point {

    private static final long serialVersionUID = -1249574512551675307L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "uuid", columnDefinition = "CHAR(22)", nullable = false, unique = true)
    private String uuid;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "category_uuid", columnDefinition = "CHAR(22)")
    private String categoryUuid;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "type", length = 100, nullable = false)
    private String type;

    @Column(name = "persistence", nullable = false)
    private boolean persistence;

    @Column(name = "remark", length = 255, nullable = true)
    private String remark;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateCategory.class)
    @JoinColumns({ //
            @JoinColumn(name = "category_uuid", referencedColumnName = "uuid", insertable = false, updatable = false), //
    })
    private HibernateCategory category;

    // -----------------------------------------------------------一对多-----------------------------------------------------------
    @OneToMany(targetEntity = HibernateTriggerSetting.class, mappedBy = "point")
    @Cascade(CascadeType.MERGE)
    private Set<HibernateTriggerSetting> triggerSettings = new HashSet<>();

    @OneToMany(targetEntity = HibernateTriggerSetting.class, mappedBy = "point")
    @Cascade(CascadeType.MERGE)
    private Set<HibernateValue> values = new HashSet<>();

    @OneToMany(targetEntity = HibernateTriggerSetting.class, mappedBy = "point")
    @Cascade(CascadeType.MERGE)
    private Set<HibernateTriggeredInfo> triggedInfos = new HashSet<>();

    public HibernatePoint() {
    }

    public HibernatePoint(
            String uuid,
            String categoryUuid,
            String name, String type,
            Boolean persistence,
            String remark,
            HibernateCategory category,
            Set<HibernateTriggerSetting> triggerSettings,
            Set<HibernateValue> values,
            Set<HibernateTriggeredInfo> triggedInfos
    ) {
        this.uuid = uuid;
        this.categoryUuid = categoryUuid;
        this.name = name;
        this.type = type;
        this.persistence = persistence;
        this.remark = remark;
        this.category = category;
        this.triggerSettings = triggerSettings;
        this.values = values;
        this.triggedInfos = triggedInfos;
    }

    @Override
    public HibernateUuidKey getKey() {
        return HibernateUuidKey.of(uuid);
    }

    public void setKey(HibernateUuidKey uuidKey) {
        this.uuid = uuidKey.getUuid();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCategoryUuid() {
        return categoryUuid;
    }

    public void setCategoryUuid(String categoryUuid) {
        this.categoryUuid = categoryUuid;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean isPersistence() {
        return persistence;
    }

    public void setPersistence(boolean persistence) {
        this.persistence = persistence;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HibernateCategory getCategory() {
        return category;
    }

    public void setCategory(HibernateCategory category) {
        this.category = category;
    }

    public Set<HibernateTriggerSetting> getTriggerSettings() {
        return triggerSettings;
    }

    public void setTriggerSettings(Set<HibernateTriggerSetting> triggerSettings) {
        this.triggerSettings = triggerSettings;
    }

    public Set<HibernateValue> getValues() {
        return values;
    }

    public void setValues(Set<HibernateValue> values) {
        this.values = values;
    }

    public Set<HibernateTriggeredInfo> getTriggedInfos() {
        return triggedInfos;
    }

    public void setTriggedInfos(Set<HibernateTriggeredInfo> triggedInfos) {
        this.triggedInfos = triggedInfos;
    }

    @Override
    public String toString() {
        return "PointImpl{" +
                "uuid='" + uuid + '\'' +
                ", categoryUuid='" + categoryUuid + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", persistence=" + persistence +
                ", remark='" + remark + '\'' +
                ", category=" + category +
                ", triggerSettings=" + triggerSettings +
                ", values=" + values +
                ", triggedInfos=" + triggedInfos +
                '}';
    }
}
