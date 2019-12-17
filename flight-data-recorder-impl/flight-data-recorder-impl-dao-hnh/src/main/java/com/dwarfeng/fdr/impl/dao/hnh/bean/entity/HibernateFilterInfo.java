package com.dwarfeng.fdr.impl.dao.hnh.bean.entity;

import com.dwarfeng.fdr.impl.dao.hnh.bean.key.HibernateUuidKey;
import com.dwarfeng.fdr.sdk.util.Constraints;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Optional;

@Entity
@IdClass(HibernateUuidKey.class)
@Table(name = "tbl_filter_info")
public class HibernateFilterInfo implements Serializable {

    private static final long serialVersionUID = -5083998565528007661L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "uuid", columnDefinition = "CHAR(22)", nullable = false, unique = true)
    private String uuid;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "point_uuid", columnDefinition = "CHAR(22)")
    private String pointUuid;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "remark", length = Constraints.LENGTH_FILTER_INFO_REMARK, nullable = true)
    private String remark;

    @Column(name = "content", columnDefinition = "TEXT", nullable = true)
    private String content;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernatePoint.class)
    @JoinColumns({ //
            @JoinColumn(name = "point_uuid", referencedColumnName = "uuid", insertable = false, updatable = false), //
    })
    private HibernatePoint point;

    public HibernateFilterInfo() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public HibernateUuidKey getKey() {
        return Optional.ofNullable(uuid).map(HibernateUuidKey::new).orElse(null);
    }

    public void setKey(HibernateUuidKey uuidKey) {
        this.uuid = Optional.ofNullable(uuidKey).map(HibernateUuidKey::getUuid).orElse(null);
    }

    public String getPointUuid() {
        return pointUuid;
    }

    public void setPointUuid(String pointUuid) {
        this.pointUuid = pointUuid;
    }

    public HibernateUuidKey getPointKey() {
        return Optional.ofNullable(pointUuid).map(HibernateUuidKey::new).orElse(null);
    }

    public void setPointKey(HibernateUuidKey parentKey) {
        this.pointUuid = Optional.ofNullable(parentKey).map(HibernateUuidKey::getUuid).orElse(null);
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

    @Override
    public String toString() {
        return "HibernateFilterInfo{" +
                "uuid='" + uuid + '\'' +
                ", pointUuid='" + pointUuid + '\'' +
                ", enabled=" + enabled +
                ", remark='" + remark + '\'' +
                ", content='" + content + '\'' +
                ", point=" + point +
                '}';
    }
}
