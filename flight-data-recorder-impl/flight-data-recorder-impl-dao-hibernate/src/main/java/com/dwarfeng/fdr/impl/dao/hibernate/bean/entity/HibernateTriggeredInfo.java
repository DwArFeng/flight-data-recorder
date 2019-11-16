package com.dwarfeng.fdr.impl.dao.hibernate.bean.entity;

import com.dwarfeng.fdr.impl.dao.hibernate.bean.key.HibernateUuidKey;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredInfo;

import javax.persistence.*;

@Entity
@IdClass(HibernateUuidKey.class)
@Table(name = "tbl_triggered_info")
public class HibernateTriggeredInfo implements TriggeredInfo {

    private static final long serialVersionUID = -4674306367461591799L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "uuid", columnDefinition = "CHAR(22)", nullable = false, unique = true)
    private String uuid;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "point_uuid", columnDefinition = "CHAR(22)")
    private String pointUuid;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateCategory.class)
    @JoinColumns({ //
            @JoinColumn(name = "point_uuid", referencedColumnName = "uuid", insertable = false, updatable = false), //
    })
    private HibernatePoint point;

    public HibernateTriggeredInfo() {
    }

    public HibernateTriggeredInfo(
            String uuid,
            String pointUuid,
            HibernatePoint point
    ) {
        this.uuid = uuid;
        this.pointUuid = pointUuid;
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

    public HibernatePoint getPoint() {
        return point;
    }

    public void setPoint(HibernatePoint point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "TriggeredInfoImpl{" +
                "uuid='" + uuid + '\'' +
                ", pointUuid='" + pointUuid + '\'' +
                ", point=" + point +
                '}';
    }
}
