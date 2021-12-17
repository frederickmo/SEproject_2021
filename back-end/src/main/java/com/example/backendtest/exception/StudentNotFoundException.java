package com.example.backendtest.exception;

/**
 * 这个显而易见吧，就是学生不存在异常
 * 因为有时候学生和教师要分别抛出异常所以单独写一个
 */
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String message) {
        super(message);
    }
}
