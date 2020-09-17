package com.getouo.frameworks;

public class ServiceException extends Exception {
    public final int statusCode;
    public final String reason;

    public ServiceException(int statusCode, String reason) {
        super("statusCode=" + statusCode + ", reason=" + reason);
        this.statusCode = statusCode;
        this.reason = reason;
    }

    public ServiceException(int statusCode, String reason, Throwable cause) {
        super("statusCode=" + statusCode + ", reason=" + reason, cause);
        this.statusCode = statusCode;
        this.reason = reason;
    }
}
