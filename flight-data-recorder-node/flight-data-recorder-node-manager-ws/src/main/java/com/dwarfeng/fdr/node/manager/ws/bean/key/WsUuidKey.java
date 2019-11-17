package com.dwarfeng.fdr.node.manager.ws.bean.key;

import com.dwarfeng.fdr.sdk.validation.DenseUUID;

import java.io.Serializable;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class WsUuidKey implements Serializable {

    @DenseUUID
    private String uuid;


}
