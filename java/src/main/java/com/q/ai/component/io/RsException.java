package com.q.ai.component.io;

/**
 *
 */
public class RsException extends RuntimeException{
    private int code;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public RsException(String message) {
        super(message);
        this.code = -100;
    }

    public RsException(String message, int code) {
        super(message);
        this.code = code;
    }
}