package com.dwarfeng.fdr.impl.bean.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.impl.bean.key.NameKeyImpl;
import com.dwarfeng.fdr.stack.bean.entity.TriggerSetting;
import com.dwarfeng.fdr.stack.bean.key.NameKey;

@Entity
@IdClass(NameKeyImpl.class)
@Table(name = "tbl_trigger_setting")
public class TriggerSettingImpl implements TriggerSetting {

	private static final long serialVersionUID = -4983401462271359522L;

	// -----------------------------------------------------------主键-----------------------------------------------------------
	@Id
	@Column(name = "name", length = 255, nullable = false, unique = true)
	@JSONField(name = "name", ordinal = 1)
	private String name;

	// -----------------------------------------------------------外键-----------------------------------------------------------
	@Column(name = "point_name", length = 255, nullable = true)
	@JSONField(name = "point_name", ordinal = 2)
	private String pointName;

	// -----------------------------------------------------------主属性字段-----------------------------------------------------------
	@Column(name = "trigger_data", columnDefinition = "TEXT", nullable = false)
	@JSONField(name = "tirgger_data", ordinal = 3)
	private String triggerData;

	@Column(name = "remark", length = 255, nullable = true)
	@JSONField(name = "remark", ordinal = 4)
	private String remark;

	// -----------------------------------------------------------多对多-----------------------------------------------------------
	@ManyToMany(targetEntity = ChannelImpl.class, mappedBy = "triggerSettingImpls")
	@Cascade(CascadeType.MERGE)
	@JSONField(serialize = false)
	private Set<ChannelImpl> channelImpls;

	public TriggerSettingImpl() {
	}

	public TriggerSettingImpl(String name, String pointName, String triggerData, String remark) {
		this.name = name;
		this.pointName = pointName;
		this.triggerData = triggerData;
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
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
	public String toString() {
		return "TriggerSettingImpl [name=" + name + ", pointName=" + pointName + ", triggerData=" + triggerData
				+ ", remark=" + remark + "]";
	}

}
