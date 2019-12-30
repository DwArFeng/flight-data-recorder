package com.dwarfeng.fdr.impl.dao.fuh.bean.entity;

import com.dwarfeng.fdr.impl.dao.fuh.bean.key.HibernateGuidKey;
import com.dwarfeng.fdr.sdk.util.Constraints;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@Entity
@IdClass(HibernateGuidKey.class)
@Table(name = "tbl_filtered_value")
public class HibernateFilteredValue implements Serializable {

    private static final long serialVersionUID = 4923865131580198476L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "guid", nullable = false, unique = true)
    private Long guid;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "point_guid")
    private Long pointGuid;

    @Column(name = "filter_guid")
    private Long filterGuid;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "happened_date", nullable = false)
    private Date happenedDate;

    @Column(name = "value", length = Constraints.LENGTH_VALUE, nullable = false)
    private String value;

    @Column(name = "message", length = Constraints.LENGTH_MESSAGE, nullable = true)
    private String message;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernatePoint.class)
    @JoinColumns({ //
            @JoinColumn(name = "point_guid", referencedColumnName = "guid", insertable = false, updatable = false), //
    })
    private HibernatePoint point;

    @ManyToOne(targetEntity = HibernateFilterInfo.class)
    @JoinColumns({ //
            @JoinColumn(name = "filter_guid", referencedColumnName = "guid", insertable = false, updatable = false), //
    })
    private HibernateFilterInfo filterInfo;

    public HibernateFilteredValue() {
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

    public HibernateGuidKey getPointKey() {
        return Optional.ofNullable(pointGuid).map(HibernateGuidKey::new).orElse(null);
    }

    public void setPointKey(HibernateGuidKey guidKey) {
        this.pointGuid = Optional.ofNullable(guidKey).map(HibernateGuidKey::getGuid).orElse(null);
    }

    public Long getPointGuid() {
        return pointGuid;
    }

    public void setPointGuid(Long pointGuid) {
        this.pointGuid = pointGuid;
    }

    public HibernateGuidKey getFilterKey() {
        return Optional.ofNullable(filterGuid).map(HibernateGuidKey::new).orElse(null);
    }

    public void setFilterKey(HibernateGuidKey guidKey) {
        this.filterGuid = Optional.ofNullable(guidKey).map(HibernateGuidKey::getGuid).orElse(null);
    }

    public Long getFilterGuid() {
        return filterGuid;
    }

    public void setFilterGuid(Long filterGuid) {
        this.filterGuid = filterGuid;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HibernatePoint getPoint() {
        return point;
    }

    public void setPoint(HibernatePoint point) {
        this.point = point;
    }

    public HibernateFilterInfo getFilterInfo() {
        return filterInfo;
    }

    public void setFilterInfo(HibernateFilterInfo filterInfo) {
        this.filterInfo = filterInfo;
    }

    @Override
    public String toString() {
        return "HibernateFilteredValue{" +
                "guid='" + guid + '\'' +
                ", pointGuid='" + pointGuid + '\'' +
                ", filterGuid='" + filterGuid + '\'' +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
