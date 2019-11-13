package com.dwarfeng.fdr.impl.dao.hibernate.bean.entity;

import com.dwarfeng.fdr.impl.dao.hibernate.bean.key.UuidKeyImpl;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredInfo;

import javax.persistence.*;

@Entity
@IdClass(UuidKeyImpl.class)
@Table(name = "tbl_triggered_info")
public class TriggeredInfoImpl implements TriggeredInfo {

    private static final long serialVersionUID = -4674306367461591799L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "uuid", columnDefinition = "CHAR(22)", nullable = false, unique = true)
    private String uuid;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "point_uuid", columnDefinition = "CHAR(22)")
    private String pointUuid;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = CategoryImpl.class)
    @JoinColumns({ //
            @JoinColumn(name = "point_uuid", referencedColumnName = "uuid", insertable = false, updatable = false), //
    })
    private PointImpl point;

    public TriggeredInfoImpl() {
    }

    public TriggeredInfoImpl(
            String uuid,
            String pointUuid,
            PointImpl point
    ) {
        this.uuid = uuid;
        this.pointUuid = pointUuid;
        this.point = point;
    }

    @Override
    public UuidKeyImpl getKey() {
        return UuidKeyImpl.of(uuid);
    }

    public void setKey(UuidKeyImpl uuidKey) {
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

    public PointImpl getPoint() {
        return point;
    }

    public void setPoint(PointImpl point) {
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
