package com.dwarfeng.fdr.stack.bean.dto;

/**
 * 错误信息。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class ErrorInfo implements Dto {

    private static final long serialVersionUID = -5922762644919894774L;

    private long code;
    private String message;

    public ErrorInfo() {
    }

    public ErrorInfo(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorInfo{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
