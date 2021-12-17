package com.example.backendtest.exception;

/**
 * 这个只有FinishesService里无法读取学生上传作业的信息时才会抛出
 */
public class FailInReadingSubmitInfoException extends RuntimeException {
    public FailInReadingSubmitInfoException(String message) {
        super(message);
    }
}
