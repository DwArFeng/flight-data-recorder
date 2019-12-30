package com.dwarfeng.fdr.node.manager.web.bean.key;

import java.io.Serializable;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class WebUuidKey implements Serializable {

    private static final long serialVersionUID = -5675443791545234988L;

    private String uuid;

    public WebUuidKey() {
    }

    public WebUuidKey(String uuid) {
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
        if (!(o instanceof WebUuidKey)) return false;

        WebUuidKey that = (WebUuidKey) o;

        return getUuid() != null ? getUuid().equals(that.getUuid()) : that.getUuid() == null;
    }

    @Override
    public int hashCode() {
        return getUuid() != null ? getUuid().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "WebUuidKey{" +
                "uuid='" + uuid + '\'' +
                '}';
    }
}
