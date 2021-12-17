package com.example.backendtest.exception;

/**
 * 邮箱已注册异常
 * 用户注册时抛出
 */
public class EmailAlreadyRegisteredException extends RuntimeException {
    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }
}
