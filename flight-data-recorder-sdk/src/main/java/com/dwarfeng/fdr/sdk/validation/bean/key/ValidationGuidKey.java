package com.dwarfeng.fdr.sdk.validation.bean.key;

import com.dwarfeng.fdr.sdk.validation.group.Insert;

import javax.validation.constraints.Positive;
import javax.validation.groups.Default;
import java.io.Serializable;
import java.util.Objects;

public class ValidationGuidKey implements Serializable {

    private static final long serialVersionUID = 3506606009628766644L;
    
    @Positive(groups = {Default.class, Insert.class})
    private long guid;

    public ValidationGuidKey() {
    }

    public ValidationGuidKey(long guid) {
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
        ValidationGuidKey that = (ValidationGuidKey) o;
        return guid == that.guid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(guid);
    }

    @Override
    public String toString() {
        return "ApiGuidKey{" +
                "guid=" + guid +
                '}';
    }
}
