package com.getouo.frameworks;

public class InvokeException extends Exception {
    public final int statusCode;
    public final String reason;

    public InvokeException(int statusCode, String reason) {
        super("statusCode=" + statusCode + ", reason=" + reason);
        this.statusCode = statusCode;
        this.reason = reason;
    }

    public InvokeException(int statusCode, String reason, Throwable cause) {
        super("statusCode=" + statusCode + ", reason=" + reason, cause);
        this.statusCode = statusCode;
        this.reason = reason;
    }
}
