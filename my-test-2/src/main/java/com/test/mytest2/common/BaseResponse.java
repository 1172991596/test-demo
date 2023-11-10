package com.test.mytest2.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 *
 * @param <T>
 * @Author zhangjieming
 */
@Data
public class BaseResponse<T> implements Serializable {

    private T data;

    private Boolean status;

    public BaseResponse(T data, Boolean status) {
        this.data = data;
        this.status = status;
    }

//    public BaseResponse(Boolean status,T data) {
//        this(data, status);
//    }

    public BaseResponse(Boolean status) {
        this(null, status);
    }
}
