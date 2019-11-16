package com.dwarfeng.fdr.impl.dao.hibernate.bean.key;

import com.dwarfeng.fdr.stack.bean.key.UuidKey;

import java.util.Objects;

public class HibernateUuidKey implements UuidKey {

    private static final long serialVersionUID = -2968659158721861267L;

    public HibernateUuidKey() {
    }

    private String uuid;

    public HibernateUuidKey(String uuid) {
        this.uuid = uuid;
    }

    public static HibernateUuidKey of(String uuid) {
        return new HibernateUuidKey(uuid);
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (Objects.isNull(o)) return false;
        if (!(o instanceof UuidKey)) return false;

        UuidKey that = (UuidKey) o;

        return Objects.equals(this.getUuid(), that.getUuid());
    }

    @Override
    public int hashCode() {
        return getUuid() != null ? getUuid().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UuidKeyImpl [uuid=" + uuid + "]";
    }


}
