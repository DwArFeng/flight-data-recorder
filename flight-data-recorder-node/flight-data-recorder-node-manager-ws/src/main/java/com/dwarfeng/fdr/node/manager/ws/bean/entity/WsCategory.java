package com.dwarfeng.fdr.node.manager.ws.bean.entity;

import com.dwarfeng.fdr.node.manager.ws.bean.key.WsUuidKey;
import com.dwarfeng.fdr.sdk.util.Constants;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class WsCategory implements Serializable {

    private static final long serialVersionUID = 8052439578130577676L;

    /**
     * 主键。
     */
    @NotNull
    @Valid
    private WsUuidKey key;
    /**
     * 名称。
     */
    @NotNull
    @Length(max = Constants.CONSTRAINT_LENGTH_CATAGORY_NAME)
    private String name;
    /**
     * 备注。
     */
    @Length(max = Constants.CONSTRAINT_LENGTH_CATAGORY_REMARK)
    private String remark;

    public WsCategory() {
    }

    public WsUuidKey getKey() {
        return key;
    }

    public void setKey(WsUuidKey key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WsCategory{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
