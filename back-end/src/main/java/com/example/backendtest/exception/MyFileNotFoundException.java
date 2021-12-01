package com.example.backendtest.exception;

public class MyFileNotFoundException extends RuntimeException {
    //找不到文件夹解释
    public MyFileNotFoundException(String message) {
        super(message);
    }

    public MyFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
