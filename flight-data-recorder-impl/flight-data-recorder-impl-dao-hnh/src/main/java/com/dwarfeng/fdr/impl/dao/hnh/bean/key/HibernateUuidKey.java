package com.dwarfeng.fdr.impl.dao.hnh.bean.key;

import java.io.Serializable;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class HibernateUuidKey implements Serializable {

    private static final long serialVersionUID = -3366231350415339034L;

    private String uuid;

    public HibernateUuidKey() {
    }

    public HibernateUuidKey(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HibernateUuidKey)) return false;

        HibernateUuidKey that = (HibernateUuidKey) o;

        return getUuid() != null ? getUuid().equals(that.getUuid()) : that.getUuid() == null;
    }

    @Override
    public int hashCode() {
        return getUuid() != null ? getUuid().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "HibernateUuidKey{" +
                "uuid='" + uuid + '\'' +
                '}';
    }
}
