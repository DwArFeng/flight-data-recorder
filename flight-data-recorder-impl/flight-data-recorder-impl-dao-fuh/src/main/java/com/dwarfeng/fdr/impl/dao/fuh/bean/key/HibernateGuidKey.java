package com.dwarfeng.fdr.impl.dao.fuh.bean.key;

import java.io.Serializable;
import java.util.Objects;

public class HibernateGuidKey implements Serializable {

    private static final long serialVersionUID = 349284417454077325L;

    private long guid;

    public HibernateGuidKey() {
    }

    public HibernateGuidKey(long guid) {
        this.guid = guid;
    }

    public long getGuid() {
        return guid;
    }

    public void setGuid(long guid) {
        this.guid = guid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HibernateGuidKey that = (HibernateGuidKey) o;
        return guid == that.guid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(guid);
    }

    @Override
    public String toString() {
        return "HibernateGuidKey{" +
                "guid=" + guid +
                '}';
    }
}
