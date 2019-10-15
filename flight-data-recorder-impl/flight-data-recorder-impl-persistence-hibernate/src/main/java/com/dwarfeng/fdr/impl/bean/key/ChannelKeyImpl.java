package com.dwarfeng.fdr.impl.bean.key;

import com.dwarfeng.fdr.stack.bean.key.ChannelKey;

public class ChannelKeyImpl implements ChannelKey {

	private static final long serialVersionUID = -8234930311901928341L;

	public static ChannelKeyImpl of(String pointName, String channelName) {
		return new ChannelKeyImpl(pointName, channelName);
	}

	private String pointName;

	private String channelName;

	public ChannelKeyImpl() {
	}

	public ChannelKeyImpl(String pointName, String channelName) {
		this.pointName = pointName;
		this.channelName = channelName;
	}

	@Override
	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	@Override
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((channelName == null) ? 0 : channelName.hashCode());
		result = prime * result + ((pointName == null) ? 0 : pointName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChannelKeyImpl other = (ChannelKeyImpl) obj;
		if (channelName == null) {
			if (other.channelName != null)
				return false;
		} else if (!channelName.equals(other.channelName))
			return false;
		if (pointName == null) {
			if (other.pointName != null)
				return false;
		} else if (!pointName.equals(other.pointName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChannelKeyImpl [pointName=" + pointName + ", channelName=" + channelName + "]";
	}

}
