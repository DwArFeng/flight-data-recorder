package com.dwarfeng.fdr.impl.persistence.hibernate.bean.entity;

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

import com.dwarfeng.fdr.impl.persistence.hibernate.bean.key.NameKeyImpl;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.NameKey;

@Entity
@IdClass(NameKeyImpl.class)
@Table(name = "tbl_point")
public class PointImpl implements Point {

	private static final long serialVersionUID = 1752777513191681552L;

	// -----------------------------------------------------------主键-----------------------------------------------------------
	@Id
	@Column(name = "name", length = 255, nullable = false, unique = true)
	private String name;

	// -----------------------------------------------------------主属性字段-----------------------------------------------------------
	@Column(name = "type", length = 100, nullable = false)
	private String type;

	@Column(name = "persistence")
	private Boolean persistence;

	@Column(name = "remark", length = 255, nullable = true)
	private String remark;

	// -----------------------------------------------------------一对多-----------------------------------------------------------
	@OneToMany(targetEntity = ChannelImpl.class, mappedBy = "pointImpl")
	@Cascade(CascadeType.MERGE)
	private Set<ChannelImpl> channelImpls = new HashSet<>();

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

	public void setKey(NameKey nameKey) {
		this.name = Optional.ofNullable(nameKey).map(NameKey::getName).orElse(null);
	}

	@Override
	public boolean isPersistence() {
		return Optional.ofNullable(persistence).map(Boolean::booleanValue).orElse(false);
	}

	public void setPersistence(boolean aFlag) {
		this.persistence = aFlag;
	}

	@Override
	public String toString() {
		return "PointImpl [name=" + name + ", type=" + type + ", persistence=" + persistence + ", remark=" + remark
				+ "]";
	}

}
