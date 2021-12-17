package com.example.backendtest.exception;

/**
 * 用户不存在异常
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
