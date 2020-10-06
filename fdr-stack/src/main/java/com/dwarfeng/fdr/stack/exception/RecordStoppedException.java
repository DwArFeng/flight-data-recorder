package com.dwarfeng.fdr.stack.exception;

import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 记录服务被禁用的异常。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public class RecordStoppedException extends HandlerException {

    private static final long serialVersionUID = 1263442110558485897L;

    public RecordStoppedException() {
    }

    public RecordStoppedException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "记录服务已经被禁用";
    }
}
