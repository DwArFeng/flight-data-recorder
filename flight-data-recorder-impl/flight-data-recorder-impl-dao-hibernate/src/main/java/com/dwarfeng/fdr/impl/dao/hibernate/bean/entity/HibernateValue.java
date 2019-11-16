package com.dwarfeng.fdr.impl.dao.hibernate.bean.entity;

import com.dwarfeng.fdr.impl.dao.hibernate.bean.key.HibernateUuidKey;
import com.dwarfeng.fdr.stack.bean.entity.Value;

import javax.persistence.*;
import java.util.Date;

@Entity
@IdClass(HibernateUuidKey.class)
@Table(name = "tbl_value")
public class HibernateValue implements Value {

    private static final long serialVersionUID = -6216892684899994076L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "uuid", columnDefinition = "CHAR(22)", nullable = false, unique = true)
    private String uuid;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "point_uuid", columnDefinition = "CHAR(22)")
    private String pointUuid;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "happened_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date happenedDate;

    @Column(name = "value", length = 255, nullable = false)
    private String value;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateCategory.class)
    @JoinColumns({ //
            @JoinColumn(name = "point_uuid", referencedColumnName = "uuid", insertable = false, updatable = false), //
    })
    private HibernatePoint point;

    public HibernateValue() {
    }

    public HibernateValue(
            String uuid,
            String pointUuid,
            Date happenedDate,
            String value,
            HibernatePoint point
    ) {
        this.uuid = uuid;
        this.pointUuid = pointUuid;
        this.happenedDate = happenedDate;
        this.value = value;
        this.point = point;
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

    public String getPointUuid() {
        return pointUuid;
    }

    public void setPointUuid(String pointUuid) {
        this.pointUuid = pointUuid;
    }

    @Override
    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    @Override
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
        return "ValueImpl{" +
                "uuid='" + uuid + '\'' +
                ", pointUuid='" + pointUuid + '\'' +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                ", point=" + point +
                '}';
    }
}
