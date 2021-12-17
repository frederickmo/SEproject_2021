package com.example.backendtest.exception;

/**
 * 这个异常只有在学生提交作业时上传文件的文件名无法读取时才会抛出
 */
public class FileNameIllegalException extends RuntimeException {
    public FileNameIllegalException(String message) {
        super(message);
    }
}
