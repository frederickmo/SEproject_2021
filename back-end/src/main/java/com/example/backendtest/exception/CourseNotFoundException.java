package com.example.backendtest.exception;

/**
 * 这个显而易见吧，就是课程不存在异常
 */
public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(String message) {
        super(message);
    }
}
