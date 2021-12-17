package com.example.backendtest.exception;

/**
 * 角色和执行功能不匹配的异常
 * 比如不能把课程的教师设置成学生
 */
public class RoleNotMatchException extends RuntimeException {
    public RoleNotMatchException(String message) {
        super(message);
    }
}
