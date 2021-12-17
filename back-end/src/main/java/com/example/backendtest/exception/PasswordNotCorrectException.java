package com.example.backendtest.exception;

/**
 * 密码错误异常
 */
public class PasswordNotCorrectException extends RuntimeException {
    public PasswordNotCorrectException(String message) {
        super(message);
    }
}
