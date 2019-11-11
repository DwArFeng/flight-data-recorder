package com.dwarfeng.fdr.impl.persistence.hibernate.bean.entity;

import com.dwarfeng.fdr.impl.persistence.hibernate.bean.key.UuidKeyImpl;
import com.dwarfeng.fdr.stack.bean.entity.TriggerSetting;

import javax.persistence.*;

@Entity
@IdClass(UuidKeyImpl.class)
@Table(name = "tbl_trigger_setting")
public class TriggerSettingImpl implements TriggerSetting {

    private static final long serialVersionUID = 6533538842633760945L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "uuid", columnDefinition = "CHAR(22)", nullable = false, unique = true)
    private String uuid;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "point_uuid", columnDefinition = "CHAR(22)")
    private String point_uuid;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "trigger_data", columnDefinition = "TEXT", nullable = false)
    private String triggerData;

    @Column(name = "remark", length = 255, nullable = true)
    private String remark;

    @Column(name = "trigger_broadcast", nullable = false)
    private boolean triggerBroadcast;

    @Column(name = "trigger_persistence", nullable = false)
    private boolean triggerPersistence;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = CategoryImpl.class)
    @JoinColumns({ //
            @JoinColumn(name = "point_uuid", referencedColumnName = "uuid", insertable = false, updatable = false), //
    })
    private PointImpl point;

    public TriggerSettingImpl() {
    }

    public TriggerSettingImpl(
            String uuid,
            String point_uuid,
            String name,
            String triggerData,
            String remark,
            boolean triggerBroadcast,
            boolean triggerPersistence,
            PointImpl point
    ) {
        this.uuid = uuid;
        this.point_uuid = point_uuid;
        this.name = name;
        this.triggerData = triggerData;
        this.remark = remark;
        this.triggerBroadcast = triggerBroadcast;
        this.triggerPersistence = triggerPersistence;
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

    public String getPoint_uuid() {
        return point_uuid;
    }

    public void setPoint_uuid(String point_uuid) {
        this.point_uuid = point_uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTriggerData() {
        return triggerData;
    }

    public void setTriggerData(String triggerData) {
        this.triggerData = triggerData;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean isTriggerBroadcast() {
        return triggerBroadcast;
    }

    public void setTriggerBroadcast(boolean triggerBroadcast) {
        this.triggerBroadcast = triggerBroadcast;
    }

    @Override
    public boolean isTriggerPersistence() {
        return triggerPersistence;
    }

    public void setTriggerPersistence(boolean triggerPersistence) {
        this.triggerPersistence = triggerPersistence;
    }

    public PointImpl getPoint() {
        return point;
    }

    public void setPoint(PointImpl point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "TriggerSettingImpl{" +
                "uuid='" + uuid + '\'' +
                ", point_uuid='" + point_uuid + '\'' +
                ", name='" + name + '\'' +
                ", triggerData='" + triggerData + '\'' +
                ", remark='" + remark + '\'' +
                ", triggerBroadcast=" + triggerBroadcast +
                ", triggerPersistence=" + triggerPersistence +
                ", point=" + point +
                '}';
    }
}
