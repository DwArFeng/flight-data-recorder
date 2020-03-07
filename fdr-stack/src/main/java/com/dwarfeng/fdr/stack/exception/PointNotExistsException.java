package com.dwarfeng.fdr.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

public class PointNotExistsException extends Exception {

    private static final long serialVersionUID = 5584249920639455084L;

    private final LongIdKey pointKey;

    public PointNotExistsException(LongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public LongIdKey getPointKey() {
        return pointKey;
    }

    @Override
    public String getMessage() {
        return "数据点不存在: " + pointKey;
    }
}
