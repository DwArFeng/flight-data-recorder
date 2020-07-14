package com.dwarfeng.fdr.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

/**
 * 实时数据被禁用异常。
 *
 * @author DwArFeng
 * @since 1.5.0
 */
public class RealtimeDisabledException extends Exception {

    private static final long serialVersionUID = -7449265335644673680L;

    private final LongIdKey pointKey;

    public RealtimeDisabledException(LongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public RealtimeDisabledException(String message, LongIdKey pointKey) {
        super(message);
        this.pointKey = pointKey;
    }

    public RealtimeDisabledException(String message, Throwable cause, LongIdKey pointKey) {
        super(message, cause);
        this.pointKey = pointKey;
    }

    public RealtimeDisabledException(Throwable cause, LongIdKey pointKey) {
        super(cause);
        this.pointKey = pointKey;
    }

    @Override
    public String getMessage() {
        return "数据点 " + pointKey + " 实时数据被禁用";
    }
}
