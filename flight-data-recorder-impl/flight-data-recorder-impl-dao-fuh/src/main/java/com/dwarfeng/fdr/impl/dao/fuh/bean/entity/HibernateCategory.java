package com.dwarfeng.fdr.impl.dao.fuh.bean.entity;

import com.dwarfeng.fdr.impl.dao.fuh.bean.key.HibernateGuidKey;
import com.dwarfeng.fdr.sdk.util.Constraints;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateGuidKey.class)
@Table(name = "tbl_category")
public class HibernateCategory implements Serializable {

    private static final long serialVersionUID = 6680055254581597361L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "guid", nullable = false, unique = true)
    private Long guid;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "parent_guid")
    private Long parentGuid;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "name", length = Constraints.LENGTH_NAME, nullable = false)
    private String name;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK, nullable = true)
    private String remark;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateCategory.class)
    @JoinColumns({ //
            @JoinColumn(name = "parent_guid", referencedColumnName = "guid", insertable = false, updatable = false), //
    })
    private HibernateCategory parentCategory;

    // -----------------------------------------------------------一对多-----------------------------------------------------------
    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateCategory.class, mappedBy = "parentCategory")
    private Set<HibernateCategory> childCategories = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernatePoint.class, mappedBy = "category")
    private Set<HibernatePoint> points = new HashSet<>();

    public HibernateCategory() {
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

    public HibernateGuidKey getParentKey() {
        return Optional.ofNullable(parentGuid).map(HibernateGuidKey::new).orElse(null);
    }

    public void setParentKey(HibernateGuidKey parentKey) {
        this.parentGuid = Optional.ofNullable(parentKey).map(HibernateGuidKey::getGuid).orElse(null);
    }

    public Long getParentGuid() {
        return parentGuid;
    }

    public void setParentGuid(Long parentGuid) {
        this.parentGuid = parentGuid;
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
                "guid='" + guid + '\'' +
                ", parentGuid='" + parentGuid + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
