package com.dwarfeng.fdr.impl.dao.fuh.bean.entity;

import com.dwarfeng.fdr.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@IdClass(HibernateLongIdKey.class)
@Table(name = "tbl_category")
public class HibernateCategory implements Bean {

    private static final long serialVersionUID = 6680055254581597361L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long longId;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "parent_id")
    private Long parentLongId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "name", length = Constraints.LENGTH_NAME, nullable = false)
    private String name;

    @Column(name = "remark", length = Constraints.LENGTH_REMARK, nullable = true)
    private String remark;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateCategory.class)
    @JoinColumns({ //
            @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false), //
    })
    private HibernateCategory parentCategory;

    // -----------------------------------------------------------一对多-----------------------------------------------------------
    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernateCategory.class, mappedBy = "parentCategory")
    private Set<HibernateCategory> childCategories = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, targetEntity = HibernatePoint.class, mappedBy = "category")
    private Set<HibernatePoint> points = new HashSet<>();

    public HibernateCategory() {
    }

    public HibernateLongIdKey getKey() {
        return Optional.ofNullable(longId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setKey(HibernateLongIdKey idKey) {
        this.longId = Optional.ofNullable(idKey).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public Long getLongId() {
        return longId;
    }

    public void setLongId(Long id) {
        this.longId = id;
    }

    public HibernateLongIdKey getParentKey() {
        return Optional.ofNullable(parentLongId).map(HibernateLongIdKey::new).orElse(null);
    }

    public void setParentKey(HibernateLongIdKey parentKey) {
        this.parentLongId = Optional.ofNullable(parentKey).map(HibernateLongIdKey::getLongId).orElse(null);
    }

    public Long getParentLongId() {
        return parentLongId;
    }

    public void setParentLongId(Long parentGuid) {
        this.parentLongId = parentGuid;
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
                "longId=" + longId +
                ", parentLongId=" + parentLongId +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
