package com.dwarfeng.fdr.stack.bean.key;

/**
 * UUID主键。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class UuidKey implements Key {

    private static final long serialVersionUID = 1763587832455987115L;

    private String uuid;

    public UuidKey() {
    }

    public UuidKey(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "UuidKey{" +
                "uuid='" + uuid + '\'' +
                '}';
    }
}
