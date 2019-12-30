package com.dwarfeng.fdr.impl.dao.fuh.bean.entity;

import com.dwarfeng.fdr.impl.dao.fuh.bean.key.HibernateGuidKey;
import com.dwarfeng.fdr.sdk.util.Constraints;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@Entity
@IdClass(HibernateGuidKey.class)
@Table(name = "tbl_persistence_value")
public class HibernatePersistenceValue implements Serializable {

    private static final long serialVersionUID = 4923865131580198476L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "guid", nullable = false, unique = true)
    private Long guid;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "point_guid")
    private Long pointGuid;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "happened_date", nullable = false)
    private Date happenedDate;

    @Column(name = "value", length = Constraints.LENGTH_VALUE, nullable = false)
    private String value;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernatePoint.class)
    @JoinColumns({ //
            @JoinColumn(name = "point_guid", referencedColumnName = "guid", insertable = false, updatable = false), //
    })
    private HibernatePoint point;

    public HibernatePersistenceValue() {
    }

    public HibernateGuidKey getKey() {
        return Optional.ofNullable(guid).map(HibernateGuidKey::new).orElse(null);
    }

    public void setKey(HibernateGuidKey key) {
        this.guid = Optional.ofNullable(key).map(HibernateGuidKey::getGuid).orElse(null);
    }

    public Long getGuid() {
        return guid;
    }

    public void setGuid(Long guid) {
        this.guid = guid;
    }

    public HibernateGuidKey getPointKey() {
        return Optional.ofNullable(pointGuid).map(HibernateGuidKey::new).orElse(null);
    }

    public void setPointKey(HibernateGuidKey pointKey) {
        this.pointGuid = Optional.ofNullable(pointKey).map(HibernateGuidKey::getGuid).orElse(null);
    }

    public Long getPointGuid() {
        return pointGuid;
    }

    public void setPointGuid(Long pointGuid) {
        this.pointGuid = pointGuid;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public HibernatePoint getPoint() {
        return point;
    }

    public void setPoint(HibernatePoint point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "HibernatePersistenceValue{" +
                "guid='" + guid + '\'' +
                ", pointGuid='" + pointGuid + '\'' +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                '}';
    }
}
