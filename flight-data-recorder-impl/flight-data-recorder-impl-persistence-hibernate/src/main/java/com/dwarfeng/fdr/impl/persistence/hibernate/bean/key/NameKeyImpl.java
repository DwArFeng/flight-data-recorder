package com.dwarfeng.fdr.impl.persistence.hibernate.bean.key;

import com.dwarfeng.fdr.stack.bean.key.NameKey;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (Objects.isNull(o)) return false;
        if (!(o instanceof NameKey)) return false;

        NameKey that = (NameKey) o;

        if (!Objects.equals(this.getName(), that.getName())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return getName() != null ? getName().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "NameKeyImpl [name=" + name + "]";
    }

}
