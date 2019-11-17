package com.dwarfeng.fdr.impl.handler.validation.bean.key;

import com.dwarfeng.fdr.sdk.validation.DenseUUID;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class ValidationUuidKey implements Serializable {

    private static final long serialVersionUID = -672024822641401262L;

    @NotNull
    @DenseUUID
    private String uuid;

    public ValidationUuidKey() {
    }

    public ValidationUuidKey(@NotNull @DenseUUID String uuid) {
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
        if (!(o instanceof ValidationUuidKey)) return false;

        ValidationUuidKey that = (ValidationUuidKey) o;

        return getUuid() != null ? getUuid().equals(that.getUuid()) : that.getUuid() == null;
    }

    @Override
    public int hashCode() {
        return getUuid() != null ? getUuid().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ValidationUuidKey{" +
                "uuid='" + uuid + '\'' +
                '}';
    }
}
