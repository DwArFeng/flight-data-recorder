package com.dwarfeng.fdr.node.manager.web.bean.key;

import com.dwarfeng.fdr.stack.bean.key.UuidKey;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class UuidKeyImpl implements UuidKey {

    private static final long serialVersionUID = 5709087570416541857L;

    @NotBlank
    private String uuid;

    public UuidKeyImpl() {
    }

    public UuidKeyImpl(String uuid) {
        this.uuid = uuid;
    }

    public static UuidKeyImpl of(String uuid) {
        return new UuidKeyImpl(uuid);
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
