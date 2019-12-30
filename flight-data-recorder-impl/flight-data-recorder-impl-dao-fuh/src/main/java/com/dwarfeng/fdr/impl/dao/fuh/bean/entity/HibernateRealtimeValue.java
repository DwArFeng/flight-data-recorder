package com.dwarfeng.fdr.impl.dao.fuh.bean.entity;

import com.dwarfeng.fdr.impl.dao.fuh.bean.key.HibernateGuidKey;
import com.dwarfeng.fdr.sdk.util.Constraints;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@Entity
@IdClass(HibernateGuidKey.class)
@Table(name = "tbl_realtime_value")
public class HibernateRealtimeValue implements Serializable {

    private static final long serialVersionUID = -8314417056307226854L;

    // -----------------------------------------------------------主键兼外键-----------------------------------------------------------
    @Id
    @Column(name = "guid", nullable = false, unique = true)
    private Long guid;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "happened_date", nullable = false)
    private Date happenedDate;

    @Column(name = "value", length = Constraints.LENGTH_VALUE, nullable = false)
    private String value;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @OneToOne(targetEntity = HibernatePoint.class)
    @JoinColumns({ //
            @JoinColumn(name = "guid", referencedColumnName = "guid", insertable = false, updatable = false), //
    })
    private HibernatePoint point;

    public HibernateRealtimeValue() {
    }

    public HibernateGuidKey getKey() {
        return Optional.ofNullable(guid).map(HibernateGuidKey::new).orElse(null);
    }

    public void setKey(HibernateGuidKey guidKey) {
        this.guid = Optional.ofNullable(guidKey).map(HibernateGuidKey::getGuid).orElse(null);
    }

    public Long getGuid() {
        return guid;
    }

    public void setGuid(Long guid) {
        this.guid = guid;
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
        return "HibernateRealtimeValue{" +
                "guid='" + guid + '\'' +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                '}';
    }
}
