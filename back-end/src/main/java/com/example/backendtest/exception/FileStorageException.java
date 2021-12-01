package com.example.backendtest.exception;

public class FileStorageException extends RuntimeException{
    //文件储存解释
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
