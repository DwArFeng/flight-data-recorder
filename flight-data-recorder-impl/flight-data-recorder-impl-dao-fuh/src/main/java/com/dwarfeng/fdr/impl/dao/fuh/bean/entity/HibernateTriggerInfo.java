package com.dwarfeng.fdr.impl.dao.fuh.bean.entity;

import com.dwarfeng.fdr.impl.dao.fuh.bean.key.HibernateGuidKey;
import com.dwarfeng.fdr.sdk.util.Constraints;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateGuidKey.class)
@Table(name = "tbl_trigger_info")
public class HibernateTriggerInfo implements Serializable {

    private static final long serialVersionUID = -1622721129538827725L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "guid", nullable = false, unique = true)
    private Long guid;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "point_guid")
    private Long pointGuid;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK, nullable = true)
    private String remark;

    @Column(name = "content", columnDefinition = "TEXT", nullable = true)
    private String content;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernatePoint.class)
    @JoinColumns({ //
            @JoinColumn(name = "point_guid", referencedColumnName = "guid", insertable = false, updatable = false), //
    })
    private HibernatePoint point;

    // -----------------------------------------------------------一对多-----------------------------------------------------------
    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateTriggeredValue.class, mappedBy = "triggerInfo")
    private Set<HibernateTriggeredValue> triggeredValues = new HashSet<>();

    public HibernateTriggerInfo() {
    }

    public Long getGuid() {
        return guid;
    }

    public void setGuid(Long guid) {
        this.guid = guid;
    }

    public HibernateGuidKey getKey() {
        return Optional.ofNullable(guid).map(HibernateGuidKey::new).orElse(null);
    }

    public void setKey(HibernateGuidKey guidKey) {
        this.guid = Optional.ofNullable(guidKey).map(HibernateGuidKey::getGuid).orElse(null);
    }

    public Long getPointGuid() {
        return pointGuid;
    }

    public void setPointGuid(Long pointGuid) {
        this.pointGuid = pointGuid;
    }

    public HibernateGuidKey getPointKey() {
        return Optional.ofNullable(pointGuid).map(HibernateGuidKey::new).orElse(null);
    }

    public void setPointKey(HibernateGuidKey parentKey) {
        this.pointGuid = Optional.ofNullable(parentKey).map(HibernateGuidKey::getGuid).orElse(null);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HibernatePoint getPoint() {
        return point;
    }

    public void setPoint(HibernatePoint point) {
        this.point = point;
    }

    public Set<HibernateTriggeredValue> getTriggeredValues() {
        return triggeredValues;
    }

    public void setTriggeredValues(Set<HibernateTriggeredValue> triggeredValues) {
        this.triggeredValues = triggeredValues;
    }

    @Override
    public String toString() {
        return "HibernateTriggerInfo{" +
                "guid='" + guid + '\'' +
                ", pointGuid='" + pointGuid + '\'' +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
