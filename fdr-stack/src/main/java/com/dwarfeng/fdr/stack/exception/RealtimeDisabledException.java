package com.dwarfeng.fdr.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;

/**
 * 实时数据被禁用异常。
 *
 * @author DwArFeng
 * @since 1.5.0
 */
public class RealtimeDisabledException extends ServiceException {

    private static final long serialVersionUID = -7449265335644673680L;

    private final LongIdKey pointKey;

    public RealtimeDisabledException(LongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public RealtimeDisabledException(Code code, LongIdKey pointKey) {
        super(code);
        this.pointKey = pointKey;
    }

    public RealtimeDisabledException(Throwable cause, LongIdKey pointKey) {
        super(cause);
        this.pointKey = pointKey;
    }

    public RealtimeDisabledException(Code code, Throwable cause, LongIdKey pointKey) {
        super(code, cause);
        this.pointKey = pointKey;
    }

    @Override
    public String getMessage() {
        return "数据点 " + pointKey + " 实时数据被禁用";
    }
}
