package com.azhang.maker.meta;

/**
 * 处理meta的异常信息
 */
public class MetaException extends RuntimeException{

    public MetaException(String message) {
        super(message);
    }

    public MetaException(String message, Throwable cause) {
        super(message, cause);
    }

}
