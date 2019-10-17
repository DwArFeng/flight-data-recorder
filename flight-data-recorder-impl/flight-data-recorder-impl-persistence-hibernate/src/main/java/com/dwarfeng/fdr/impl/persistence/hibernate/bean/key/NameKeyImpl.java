package com.dwarfeng.fdr.impl.persistence.hibernate.bean.key;

import com.dwarfeng.fdr.stack.bean.key.NameKey;

public class NameKeyImpl implements NameKey {

	private static final long serialVersionUID = 8861447613889029550L;

	public static NameKeyImpl of(String name) {
		return new NameKeyImpl(name);
	}

	private String name;

	public NameKeyImpl() {
	}

	public NameKeyImpl(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "NameKeyImpl [name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		NameKeyImpl other = (NameKeyImpl) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
