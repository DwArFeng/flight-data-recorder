package com.dwarfeng.fdr.impl.dao.fuh.bean.entity;

import com.dwarfeng.fdr.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_trigger_info")
public class HibernateTriggerInfo implements Bean {

    private static final long serialVersionUID = -1622721129538827725L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "point_id")
    private Long pointLongId;

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
            @JoinColumn(name = "point_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernatePoint point;

    // -----------------------------------------------------------一对多-----------------------------------------------------------
    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateTriggeredValue.class, mappedBy = "triggerInfo")
    private Set<HibernateTriggeredValue> triggeredValues = new HashSet<>();

    public HibernateTriggerInfo() {
    }

    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long id) {
        this.longId = id;
    }

    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey idKey) {
        this.longId = Optional.ofNullable(idKey).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public Long getPointLongId() {
        return pointLongId;
    }

    public void setPointLongId(Long pointGuid) {
        this.pointLongId = pointGuid;
    }

    public HibernateLongIdKey getPointKey() {
        return Optional.ofNullable(pointLongId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setPointKey(HibernateLongIdKey parentKey) {
        this.pointLongId = Optional.ofNullable(parentKey).map(HibernateLongIdKey::getLongId).orElse(null);
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
                "longId=" + longId +
                ", pointLongId=" + pointLongId +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
