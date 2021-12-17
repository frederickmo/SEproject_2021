package com.example.backendtest.exception;

/**
 * 这个是所有查询结果不存在的通用异常，
 * 需要具体到什么异常的话，就查看XXXNotFoundException
 * 如果不需要区分的话，就抛出这个就行了。
 *
 * 这个似乎和java原生的一个异常重了，不知道有啥后果。。。。。。
 * ↓
 * 不知道啥后果，模仿一下MyFileNotFoundException改个名吧。
 */
public class MyNotFoundException extends RuntimeException {
    public MyNotFoundException(String message) {
        super(message);
    }
}
