package com.dwarfeng.fdr.impl.dao.fuh.bean.entity;

import com.dwarfeng.fdr.impl.dao.fuh.bean.key.HibernateUuidKey;
import com.dwarfeng.fdr.sdk.util.Constraints;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateUuidKey.class)
@Table(name = "tbl_point")
public class HibernatePoint implements Serializable {

    private static final long serialVersionUID = 4923865131580198476L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "uuid", columnDefinition = "CHAR(22)", nullable = false, unique = true)
    private String uuid;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "category_uuid", columnDefinition = "CHAR(22)")
    private String categoryUuid;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "name", length = Constraints.LENGTH_NAME, nullable = false)
    private String name;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK, nullable = true)
    private String remark;

    @Column(name = "persistence_enabled", nullable = false)
    private boolean persistenceEnabled;

    @Column(name = "realtime_enabled", nullable = false)
    private boolean realtimeEnabled;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateCategory.class)
    @JoinColumns({ //
            @JoinColumn(name = "category_uuid", referencedColumnName = "uuid", insertable = false, updatable = false), //
    })
    private HibernateCategory category;

    // -----------------------------------------------------------一对多-----------------------------------------------------------
    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateFilterInfo.class, mappedBy = "point")
    private Set<HibernateFilterInfo> filterInfos = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateTriggerInfo.class, mappedBy = "point")
    private Set<HibernateTriggerInfo> triggerInfos = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateFilteredValue.class, mappedBy = "point")
    private Set<HibernateFilteredValue> filteredValues = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernatePersistenceValue.class, mappedBy = "point")
    private Set<HibernatePersistenceValue> persistenceValues = new HashSet<>();

    @OneToOne(cascade = CascadeType.MERGE, targetEntity = HibernateRealtimeValue.class, mappedBy = "point")
    private HibernateRealtimeValue realtimeValue;

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateTriggeredValue.class, mappedBy = "point")
    private Set<HibernateTriggeredValue> triggeredValues = new HashSet<>();

//    @OneToMany(targetEntity = PointHibernateImpl.class, mappedBy = "category")
//    @Cascade(CascadeType.MERGE)
//    private Set<PointHibernateImpl> points = new HashSet<>();

    public HibernatePoint() {
    }

    public HibernateUuidKey getKey() {
        return Optional.ofNullable(uuid).map(HibernateUuidKey::new).orElse(null);
    }

    public void setKey(HibernateUuidKey uuidKey) {
        this.uuid = Optional.ofNullable(uuidKey).map(HibernateUuidKey::getUuid).orElse(null);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public HibernateUuidKey getCategoryKey() {
        return Optional.ofNullable(categoryUuid).map(HibernateUuidKey::new).orElse(null);
    }

    public void setCategoryKey(HibernateUuidKey parentKey) {
        this.categoryUuid = Optional.ofNullable(parentKey).map(HibernateUuidKey::getUuid).orElse(null);
    }

    public String getCategoryUuid() {
        return categoryUuid;
    }

    public void setCategoryUuid(String categoryUuid) {
        this.categoryUuid = categoryUuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isPersistenceEnabled() {
        return persistenceEnabled;
    }

    public void setPersistenceEnabled(boolean persistenceEnabled) {
        this.persistenceEnabled = persistenceEnabled;
    }

    public boolean isRealtimeEnabled() {
        return realtimeEnabled;
    }

    public void setRealtimeEnabled(boolean realtimeEnabled) {
        this.realtimeEnabled = realtimeEnabled;
    }

    public HibernateCategory getCategory() {
        return category;
    }

    public void setCategory(HibernateCategory category) {
        this.category = category;
    }

    public Set<HibernateFilterInfo> getFilterInfos() {
        return filterInfos;
    }

    public void setFilterInfos(Set<HibernateFilterInfo> filterInfos) {
        this.filterInfos = filterInfos;
    }

    public Set<HibernateTriggerInfo> getTriggerInfos() {
        return triggerInfos;
    }

    public void setTriggerInfos(Set<HibernateTriggerInfo> triggerInfos) {
        this.triggerInfos = triggerInfos;
    }

    public Set<HibernateFilteredValue> getFilteredValues() {
        return filteredValues;
    }

    public void setFilteredValues(Set<HibernateFilteredValue> filteredValues) {
        this.filteredValues = filteredValues;
    }

    public Set<HibernatePersistenceValue> getPersistenceValues() {
        return persistenceValues;
    }

    public void setPersistenceValues(Set<HibernatePersistenceValue> persistenceValues) {
        this.persistenceValues = persistenceValues;
    }

    public HibernateRealtimeValue getRealtimeValue() {
        return realtimeValue;
    }

    public void setRealtimeValue(HibernateRealtimeValue realtimeValue) {
        this.realtimeValue = realtimeValue;
    }

    public Set<HibernateTriggeredValue> getTriggeredValues() {
        return triggeredValues;
    }

    public void setTriggeredValues(Set<HibernateTriggeredValue> triggeredValues) {
        this.triggeredValues = triggeredValues;
    }

    @Override
    public String toString() {
        return "HibernatePoint{" +
                "uuid='" + uuid + '\'' +
                ", categoryUuid='" + categoryUuid + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", persistenceEnabled=" + persistenceEnabled +
                ", realtimeEnabled=" + realtimeEnabled +
                '}';
    }
}
