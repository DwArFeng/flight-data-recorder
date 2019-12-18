package com.dwarfeng.fdr.impl.dao.hnh.bean.entity;

import com.dwarfeng.fdr.impl.dao.hnh.bean.key.HibernateUuidKey;
import com.dwarfeng.fdr.sdk.util.Constraints;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateUuidKey.class)
@Table(name = "tbl_category")
public class HibernateCategory implements Serializable {

    private static final long serialVersionUID = 6680055254581597361L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "uuid", columnDefinition = "CHAR(22)", nullable = false, unique = true)
    private String uuid;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "parent_uuid", columnDefinition = "CHAR(22)")
    private String parentUuid;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "name", length = Constraints.LENGTH_NAME, nullable = false)
    private String name;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK, nullable = true)
    private String remark;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateCategory.class)
    @JoinColumns({ //
            @JoinColumn(name = "parent_uuid", referencedColumnName = "uuid", insertable = false, updatable = false), //
    })
    private HibernateCategory parentCategory;

    // -----------------------------------------------------------一对多-----------------------------------------------------------
    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateCategory.class, mappedBy = "parentCategory")
    private Set<HibernateCategory> childCategories = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernatePoint.class, mappedBy = "category")
    private Set<HibernatePoint> points = new HashSet<>();

//    @OneToMany(targetEntity = PointHibernateImpl.class, mappedBy = "category")
//    @Cascade(CascadeType.MERGE)
//    private Set<PointHibernateImpl> points = new HashSet<>();

    public HibernateCategory() {
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

    public HibernateUuidKey getParentKey() {
        return Optional.ofNullable(parentUuid).map(HibernateUuidKey::new).orElse(null);
    }

    public void setParentKey(HibernateUuidKey parentKey) {
        this.parentUuid = Optional.ofNullable(parentKey).map(HibernateUuidKey::getUuid).orElse(null);
    }

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public HibernateCategory getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(HibernateCategory parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Set<HibernateCategory> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(Set<HibernateCategory> childCategories) {
        this.childCategories = childCategories;
    }

    @Override
    public String toString() {
        return "HibernateCategory{" +
                "uuid='" + uuid + '\'' +
                ", parentUuid='" + parentUuid + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
