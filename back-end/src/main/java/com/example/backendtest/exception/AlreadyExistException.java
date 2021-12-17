package com.example.backendtest.exception;

/**
 * 已存在异常：查询结果已存在
 * 向数据库新增数据时抛出
 */
public class AlreadyExistException extends RuntimeException {

    public AlreadyExistException(String message) {
        super(message);
    }

    public AlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
