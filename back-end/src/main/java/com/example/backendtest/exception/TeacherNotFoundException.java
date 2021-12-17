package com.example.backendtest.exception;

/**
 * 这个显而易见吧，就是教师不存在异常
 * 因为有时候学生和教师要分别抛出异常所以单独写一个
 */
public class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException(String message) {
        super(message);
    }
}
