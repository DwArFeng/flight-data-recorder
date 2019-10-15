package com.dwarfeng.fdr.impl.bean.entity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.impl.bean.key.NameKeyImpl;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.NameKey;

@Entity
@IdClass(NameKeyImpl.class)
@Table(name = "tbl_point")
public class PointImpl implements Point {

	private static final long serialVersionUID = 6516345958302170422L;

	// -----------------------------------------------------------主键-----------------------------------------------------------
	@Id
	@Column(name = "name", length = 255, nullable = false, unique = true)
	@JSONField(name = "name", ordinal = 1)
	private String name;

	// -----------------------------------------------------------主属性字段-----------------------------------------------------------
	@Column(name = "type", length = 100, nullable = false)
	@JSONField(name = "type", ordinal = 2)
	private String type;

	@Column(name = "persistence")
	@JSONField(name = "persistence", ordinal = 3)
	private Boolean persistence;

	@Column(name = "remark", length = 255, nullable = true)
	@JSONField(name = "remark", ordinal = 4)
	private String remark;

	// -----------------------------------------------------------一对多-----------------------------------------------------------
	@OneToMany(targetEntity = ChannelImpl.class, mappedBy = "pointImpl")
	@Cascade(CascadeType.MERGE)
	@JSONField(serialize = false)
	private transient Set<ChannelImpl> channelImpls = new HashSet<>();

	public PointImpl() {
	}

	public PointImpl(String name, String type, String remark) {
		this.name = name;
		this.type = type;
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getPersistence() {
		return persistence;
	}

	public void setPersistence(Boolean persistence) {
		this.persistence = persistence;
	}

	@Override
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<ChannelImpl> getChannelImpls() {
		return channelImpls;
	}

	public void setChannelImpls(Set<ChannelImpl> channelImpls) {
		this.channelImpls = channelImpls;
	}

	@Override
	public NameKey getKey() {
		return NameKeyImpl.of(name);
	}

	@Override
	public boolean isPersistence() {
		return Optional.ofNullable(persistence).map(Boolean::booleanValue).orElse(false);
	}

	@Override
	public String toString() {
		return "PointImpl [name=" + name + ", type=" + type + ", remark=" + remark + "]";
	}

}
