package com.dwarfeng.fdr.impl.dao.hibernate.bean.entity;

import com.dwarfeng.fdr.impl.dao.hibernate.bean.key.HibernateUuidKey;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@IdClass(HibernateUuidKey.class)
@Table(name = "tbl_category")
public class HibernateCategory implements Category {

    private static final long serialVersionUID = 583598193113136019L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "uuid", columnDefinition = "CHAR(22)", nullable = false, unique = true)
    private String uuid;

    // -----------------------------------------------------------外键-----------------------------------------------------------
    @Column(name = "parent_uuid", columnDefinition = "CHAR(22)")
    private String parentUuid;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "remark", length = 255, nullable = true)
    private String remark;

    // -----------------------------------------------------------多对一-----------------------------------------------------------
    @ManyToOne(targetEntity = HibernateCategory.class)
    @JoinColumns({ //
            @JoinColumn(name = "parent_uuid", referencedColumnName = "uuid", insertable = false, updatable = false), //
    })
    private HibernateCategory parentCategory;

    // -----------------------------------------------------------一对多-----------------------------------------------------------
    @OneToMany(targetEntity = HibernateCategory.class, mappedBy = "parentCategory")
    @Cascade(CascadeType.MERGE)
    private Set<HibernateCategory> childCategories = new HashSet<>();

    @OneToMany(targetEntity = HibernatePoint.class, mappedBy = "category")
    @Cascade(CascadeType.MERGE)
    private Set<HibernatePoint> points = new HashSet<>();

    public HibernateCategory() {
    }

    public HibernateCategory(
            String uuid,
            String parentUuid,
            String name,
            String remark,
            HibernateCategory parentCategory,
            Set<HibernateCategory> childCategories,
            Set<HibernatePoint> points
    ) {
        this.uuid = uuid;
        this.parentUuid = parentUuid;
        this.name = name;
        this.remark = remark;
        this.parentCategory = parentCategory;
        this.childCategories = childCategories;
        this.points = points;
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

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
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

    public Set<HibernatePoint> getPoints() {
        return points;
    }

    public void setPoints(Set<HibernatePoint> points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "CategoryImpl{" +
                "uuid='" + uuid + '\'' +
                ", parentUuid='" + parentUuid + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", parentCategory=" + parentCategory +
                ", childCategories=" + childCategories +
                ", points=" + points +
                '}';
    }
}
