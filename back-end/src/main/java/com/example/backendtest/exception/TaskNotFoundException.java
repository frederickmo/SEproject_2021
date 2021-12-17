package com.example.backendtest.exception;

/**
 * 这个显而易见吧，就是实验项目不存在异常
 */
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
