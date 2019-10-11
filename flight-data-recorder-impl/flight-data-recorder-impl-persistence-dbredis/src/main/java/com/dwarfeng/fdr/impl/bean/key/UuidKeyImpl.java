package com.dwarfeng.fdr.impl.bean.key;

import com.dwarfeng.fdr.stack.bean.key.UuidKey;

public class UuidKeyImpl implements UuidKey {

	private static final long serialVersionUID = -2968659158721861267L;

	public static UuidKeyImpl of(String uuid) {
		return new UuidKeyImpl(uuid);
	}

	private String uuid;

	public UuidKeyImpl() {
	}

	public UuidKeyImpl(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "UuidKeyImpl [uuid=" + uuid + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
		UuidKeyImpl other = (UuidKeyImpl) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

}
