package com.dwarfeng.fdr.impl.bean.commonbean.bean.key;

import com.dwarfeng.fdr.stack.bean.key.UuidKey;

import java.util.Objects;

public class CommonUuidKey implements UuidKey {

    private static final long serialVersionUID = -6928697057206505310L;

    public CommonUuidKey() {
    }

    private String uuid;

    public CommonUuidKey(String uuid) {
        this.uuid = uuid;
    }

    public static CommonUuidKey of(String uuid) {
        return new CommonUuidKey(uuid);
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
