package com.dwarfeng.fdr.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;

/**
 * 持久化数据被禁用异常。
 *
 * @author DwArFeng
 * @since 1.5.0
 */
public class PersistenceDisabledException extends ServiceException {

    private static final long serialVersionUID = 502323031960756558L;

    private final LongIdKey pointKey;

    public PersistenceDisabledException(LongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public PersistenceDisabledException(Code code, LongIdKey pointKey) {
        super(code);
        this.pointKey = pointKey;
    }

    public PersistenceDisabledException(Throwable cause, LongIdKey pointKey) {
        super(cause);
        this.pointKey = pointKey;
    }

    public PersistenceDisabledException(Code code, Throwable cause, LongIdKey pointKey) {
        super(code, cause);
        this.pointKey = pointKey;
    }

    @Override
    public String getMessage() {
        return "数据点 " + pointKey + " 持久化数据被禁用";
    }
}
