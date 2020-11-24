package com.webflux.demo.exception;

import lombok.Data;

/**
 * webFlux
 * com.webflux.demo.exception
 * CheckException
 * 2019/11/28 8:59
 * author:Euraxluo
 * TODO
 */
@Data
public class CheckException extends RuntimeException{

    private String fieldValue;
    private String fieldName;

    public CheckException() {
        super();
    }

    public CheckException(String message) {
        super(message);
    }

    public CheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckException(Throwable cause) {
        super(cause);
    }

    public CheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CheckException(String fieldValue, String fieldName) {
        super();
        this.fieldValue = fieldValue;
        this.fieldName = fieldName;
    }
}
