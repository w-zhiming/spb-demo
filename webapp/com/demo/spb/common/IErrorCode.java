package com.demo.spb.common;

/**
 * 封装API的错误码
 * Created by bill on 2020/4/16.
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
