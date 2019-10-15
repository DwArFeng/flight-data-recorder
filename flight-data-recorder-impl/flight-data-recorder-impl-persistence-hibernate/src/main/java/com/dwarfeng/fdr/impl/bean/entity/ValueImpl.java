package com.dwarfeng.fdr.impl.bean.entity;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dwarfeng.fdr.impl.bean.key.UuidKeyImpl;
import com.dwarfeng.fdr.stack.bean.entity.Value;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;

@Entity
@IdClass(UuidKeyImpl.class)
@Table(name = "tbl_value")
public class ValueImpl implements Value {

	private static final long serialVersionUID = 1345475572253749699L;

	// -----------------------------------------------------------主键-----------------------------------------------------------
	@Id
	@Column(name = "uuid", columnDefinition = "CHAR(22)", nullable = false, unique = true)
	private String uuid;

	// -----------------------------------------------------------外键-----------------------------------------------------------
	@Column(name = "channel_point_name", length = 255, nullable = true)
	private String channelPointName;

	@Column(name = "channel_channel_name", length = 255, nullable = true)
	private String channelChannelName;

	@Column(name = "pervious_value_uuid", columnDefinition = "CHAR(22)", nullable = false, unique = true)
	private String perviousValueUuid;

	@Column(name = "next_value_uuid", columnDefinition = "CHAR(22)", nullable = false, unique = true)
	private String nextValueUuid;

	// -----------------------------------------------------------主属性字段-----------------------------------------------------------
	@Column(name = "happened_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date happenedDate;

	@Column(name = "value", length = 255, nullable = false)
	private String value;

	// -----------------------------------------------------------一对一-----------------------------------------------------------
	@OneToOne(targetEntity = ValueImpl.class)
	@JoinColumns({ //
			@JoinColumn(name = "pervious_value_uuid", referencedColumnName = "uuid", insertable = false, updatable = false) //
	})
	private ValueImpl perviousValueImpl;

	@OneToOne(targetEntity = ValueImpl.class)
	@JoinColumns({ //
			@JoinColumn(name = "next_value_uuid", referencedColumnName = "uuid", insertable = false, updatable = false) //
	})
	private ValueImpl nextValueImpl;

	// -----------------------------------------------------------多对一-----------------------------------------------------------
	@ManyToOne(targetEntity = ChannelImpl.class)
	@JoinColumns({ //
			@JoinColumn(name = "channel_point_name", referencedColumnName = "point_name", insertable = false, updatable = false), //
			@JoinColumn(name = "channel_channel_name", referencedColumnName = "channel_name", insertable = false, updatable = false) //
	})
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

	public String getPerviousValueUuid() {
		return perviousValueUuid;
	}

	public void setPerviousValueUuid(String perviousValueUuid) {
		this.perviousValueUuid = perviousValueUuid;
	}

	public String getNextValueUuid() {
		return nextValueUuid;
	}

	public void setNextValueUuid(String nextValueUuid) {
		this.nextValueUuid = nextValueUuid;
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

	public ValueImpl getPerviousValueImpl() {
		return perviousValueImpl;
	}

	public void setPerviousValueImpl(ValueImpl perviousValueImpl) {
		this.perviousValueImpl = perviousValueImpl;
	}

	public ValueImpl getNextValueImpl() {
		return nextValueImpl;
	}

	public void setNextValueImpl(ValueImpl nextValueImpl) {
		this.nextValueImpl = nextValueImpl;
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

	public void setKey(UuidKey uuidKey) {
		this.uuid = Optional.ofNullable(uuidKey).map(UuidKey::getUuid).orElse(null);
	}

	@Override
	public String toString() {
		return "ValueImpl [uuid=" + uuid + ", channelPointName=" + channelPointName + ", channelChannelName="
				+ channelChannelName + ", perviousValueUuid=" + perviousValueUuid + ", nextValueUuid=" + nextValueUuid
				+ ", happenedDate=" + happenedDate + ", value=" + value + "]";
	}

}
