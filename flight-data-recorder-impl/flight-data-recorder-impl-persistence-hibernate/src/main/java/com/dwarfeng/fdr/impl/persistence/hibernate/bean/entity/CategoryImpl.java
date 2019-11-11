package com.dwarfeng.fdr.impl.persistence.hibernate.bean.entity;

import com.dwarfeng.fdr.impl.persistence.hibernate.bean.key.UuidKeyImpl;
import com.dwarfeng.fdr.stack.bean.entity.Category;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@IdClass(UuidKeyImpl.class)
@Table(name = "tbl_category")
public class CategoryImpl implements Category {

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
    @ManyToOne(targetEntity = CategoryImpl.class)
    @JoinColumns({ //
            @JoinColumn(name = "parent_uuid", referencedColumnName = "uuid", insertable = false, updatable = false), //
    })
    private CategoryImpl parentCategory;

    // -----------------------------------------------------------一对多-----------------------------------------------------------
    @OneToMany(targetEntity = CategoryImpl.class, mappedBy = "parentCategory")
    @Cascade(CascadeType.MERGE)
    private Set<CategoryImpl> childCategories = new HashSet<>();

    @OneToMany(targetEntity = PointImpl.class, mappedBy = "category")
    @Cascade(CascadeType.MERGE)
    private Set<PointImpl> points = new HashSet<>();

    public CategoryImpl() {
    }

    public CategoryImpl(
            String uuid,
            String parentUuid,
            String name,
            String remark,
            CategoryImpl parentCategory,
            Set<CategoryImpl> childCategories,
            Set<PointImpl> points
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

    public CategoryImpl getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(CategoryImpl parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Set<CategoryImpl> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(Set<CategoryImpl> childCategories) {
        this.childCategories = childCategories;
    }

    public Set<PointImpl> getPoints() {
        return points;
    }

    public void setPoints(Set<PointImpl> points) {
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
