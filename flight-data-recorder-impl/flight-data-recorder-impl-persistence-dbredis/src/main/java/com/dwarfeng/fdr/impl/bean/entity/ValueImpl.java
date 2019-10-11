package com.dwarfeng.fdr.impl.bean.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.impl.bean.key.UuidKeyImpl;
import com.dwarfeng.fdr.stack.bean.entity.Value;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

@Entity
@IdClass(UuidKeyImpl.class)
@Table(name = "tbl_value")
public class ValueImpl implements Value {

	private static final long serialVersionUID = 907505514093727464L;

	// -----------------------------------------------------------主键-----------------------------------------------------------
	@Id
	@Column(name = "uuid", columnDefinition = "CHAR(22)", nullable = false, unique = true)
	@JSONField(name = "uuid", ordinal = 1)
	private String uuid;

	// -----------------------------------------------------------外键-----------------------------------------------------------
	@Column(name = "channel_point_name", length = 255, nullable = true)
	@JSONField(name = "channel_point_name", ordinal = 2)
	private String channelPointName;

	@Column(name = "channel_channel_name", length = 255, nullable = true)
	@JSONField(name = "channel_channel_name", ordinal = 3)
	private String channelChannelName;

	// -----------------------------------------------------------主属性字段-----------------------------------------------------------
	@Column(name = "happened_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JSONField(name = "happened_date", ordinal = 4)
	private Date happenedDate;

	@Column(name = "value", length = 255, nullable = false)
	@JSONField(name = "value", ordinal = 5)
	private String value;

	// -----------------------------------------------------------多对一-----------------------------------------------------------
	@ManyToOne(targetEntity = ChannelImpl.class)
	@JoinColumns({ //
			@JoinColumn(name = "channel_point_name", referencedColumnName = "point_name", insertable = false, updatable = false), //
			@JoinColumn(name = "channel_channel_name", referencedColumnName = "channel_name", insertable = false, updatable = false) //
	})
	@JSONField(serialize = false)
	private ChannelImpl channelImpl;

	public ValueImpl() {
	}

	public ValueImpl(String uuid, String channelPointName, String channelChannelName, Date happenedDate, String value) {
		this.uuid = uuid;
		this.channelPointName = channelPointName;
		this.channelChannelName = channelChannelName;
		this.happenedDate = happenedDate;
		this.value = value;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getChannelPointName() {
		return channelPointName;
	}

	public void setChannelPointName(String channelPointName) {
		this.channelPointName = channelPointName;
	}

	public String getChannelChannelName() {
		return channelChannelName;
	}

	public void setChannelChannelName(String channelChannelName) {
		this.channelChannelName = channelChannelName;
	}

	@Override
	public Date getHappenedDate() {
		return happenedDate;
	}

	public void setHappenedDate(Date happenedDate) {
		this.happenedDate = happenedDate;
	}

	@Override
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ChannelImpl getChannelImpl() {
		return channelImpl;
	}

	public void setChannelImpl(ChannelImpl channelImpl) {
		this.channelImpl = channelImpl;
	}

	@Override
	public UuidKey getKey() {
		return UuidKeyImpl.of(uuid);
	}

	@Override
	public String toString() {
		return "ValueImpl [uuid=" + uuid + ", channelPointName=" + channelPointName + ", channelChannelName="
				+ channelChannelName + ", happenedDate=" + happenedDate + ", value=" + value + "]";
	}

}
