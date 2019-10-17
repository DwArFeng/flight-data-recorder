package com.dwarfeng.fdr.impl.persistence.hibernate.bean.entity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.dwarfeng.fdr.impl.persistence.hibernate.bean.key.ChannelKeyImpl;
import com.dwarfeng.fdr.stack.bean.entity.Channel;
import com.dwarfeng.fdr.stack.bean.key.ChannelKey;

@Entity
@IdClass(ChannelKeyImpl.class)
@Table(name = "tbl_channel")
public class ChannelImpl implements Channel {

	private static final long serialVersionUID = -4968787286247679081L;

	// -----------------------------------------------------------主键-----------------------------------------------------------
	@Id
	@Column(name = "point_name", length = 255, nullable = false)
	private String pointName;

	@Id
	@Column(name = "channel_name", length = 255, nullable = false)
	private String channelName;

	// -----------------------------------------------------------主属性字段-----------------------------------------------------------
	@Column(name = "default_channel")
	private Boolean defaultChannel;

	@Column(name = "enabled")
	private Boolean enabled;

	@Column(name = "remark", length = 255, nullable = true)
	private String remark;

	// -----------------------------------------------------------多对一-----------------------------------------------------------
	@ManyToOne(targetEntity = PointImpl.class)
	@JoinColumns({ //
			@JoinColumn(name = "point_name", referencedColumnName = "name", insertable = false, updatable = false), //
	})
	private PointImpl pointImpl;

	// -----------------------------------------------------------一对多-----------------------------------------------------------
	@OneToMany(targetEntity = ValueImpl.class, mappedBy = "channelImpl")
	@Cascade(CascadeType.MERGE)
	private Set<ValueImpl> valueImpls = new HashSet<>();

	// -----------------------------------------------------------多对多-----------------------------------------------------------
	@ManyToMany(targetEntity = TriggerSettingImpl.class)
	@JoinTable(name = "tbl_channel_has_trigger_setting", joinColumns = { //
			@JoinColumn(name = "channel_point_name", referencedColumnName = "point_name"), //
			@JoinColumn(name = "channel_channel_name", referencedColumnName = "channel_name")//
	}, inverseJoinColumns = { //
			@JoinColumn(name = "trigger_setting_name", referencedColumnName = "name")//
	})
	private Set<TriggerSettingImpl> triggerSettingImpls = new HashSet<>();

	public ChannelImpl() {
	}

	public ChannelImpl(String pointName, String channelName, Boolean defaultChannel, Boolean enabled, String remark) {
		this.pointName = pointName;
		this.channelName = channelName;
		this.defaultChannel = defaultChannel;
		this.enabled = enabled;
		this.remark = remark;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Boolean getDefaultChannel() {
		return defaultChannel;
	}

	public void setDefaultChannel(Boolean defaultChannel) {
		this.defaultChannel = defaultChannel;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public PointImpl getPointImpl() {
		return pointImpl;
	}

	public void setPointImpl(PointImpl pointImpl) {
		this.pointImpl = pointImpl;
	}

	public Set<ValueImpl> getValueImpls() {
		return valueImpls;
	}

	public void setValueImpls(Set<ValueImpl> valueImpls) {
		this.valueImpls = valueImpls;
	}

	public Set<TriggerSettingImpl> getTriggerSettingImpls() {
		return triggerSettingImpls;
	}

	public void setTriggerSettingImpls(Set<TriggerSettingImpl> triggerSettingImpls) {
		this.triggerSettingImpls = triggerSettingImpls;
	}

	@Override
	public ChannelKey getKey() {
		return ChannelKeyImpl.of(pointName, channelName);
	}

	public void setKey(ChannelKey channelKey) {
		this.pointName = Optional.ofNullable(channelKey).map(ChannelKey::getPointName).orElse(null);
		this.channelName = Optional.ofNullable(channelKey).map(ChannelKey::getChannelName).orElse(null);
	}

	@Override
	public boolean isDefaultChannel() {
		return Optional.of(defaultChannel).map(Boolean::booleanValue).orElse(false);
	}

	public void setDefaultChannel(boolean aFlag) {
		this.defaultChannel = aFlag;
	}

	@Override
	public boolean isEnabled() {
		return Optional.of(enabled).map(Boolean::booleanValue).orElse(false);
	}

	public void setEnabled(boolean aFlag) {
		this.enabled = aFlag;
	}

	@Override
	public String toString() {
		return "ChannelImpl [pointName=" + pointName + ", channelName=" + channelName + ", defaultChannel="
				+ defaultChannel + ", enabled=" + enabled + ", remark=" + remark + "]";
	}

}
