package com.dwarfeng.fdr.impl.dao.fuh.bean.entity;

import com.dwarfeng.fdr.impl.dao.fuh.bean.key.HibernateUuidKey;
import com.dwarfeng.fdr.sdk.util.Constraints;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@Entity
@IdClass(HibernateUuidKey.class)
@Table(name = "tbl_realtime_value")
public class HibernateRealtimeValue implements Serializable {

    private static final long serialVersionUID = -8314417056307226854L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "uuid", columnDefinition = "CHAR(22)", nullable = false, unique = true)
    private String uuid;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "point_uuid", columnDefinition = "CHAR(22)")
    private String pointUuid;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "happened_date", nullable = false)
    private Date happenedDate;

    @Column(name = "value", length = Constraints.LENGTH_REALTIME_VALUE_VALUE, nullable = false)
    private String value;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernatePoint.class)
    @JoinColumns({ //
            @JoinColumn(name = "point_uuid", referencedColumnName = "uuid", insertable = false, updatable = false), //
    })
    private HibernatePoint point;

    public HibernateRealtimeValue() {
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

    public String getPointUuid() {
        return pointUuid;
    }

    public void setPointUuid(String pointUuid) {
        this.pointUuid = pointUuid;
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
                "uuid='" + uuid + '\'' +
                ", pointUuid='" + pointUuid + '\'' +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                '}';
    }
}