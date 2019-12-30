package com.dwarfeng.fdr.stack.bean.key;

import java.util.Objects;

public class GuidKey implements Key {

    private static final long serialVersionUID = 1741377415614295616L;

    private long guid;

    public GuidKey() {
    }

    public GuidKey(long guid) {
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
        GuidKey guidKey = (GuidKey) o;
        return guid == guidKey.guid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(guid);
    }

    @Override
    public String toString() {
        return "GuidKey{" +
                "guid=" + guid +
                '}';
    }
}
