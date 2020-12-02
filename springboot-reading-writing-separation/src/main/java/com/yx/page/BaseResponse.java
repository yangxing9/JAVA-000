package com.yx.page;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {

    public static final String ERROR_CODE = "1";
    public static final String SUCCESS_MESSAGE = "success";

    private String code = "0";
    private String message;
    private T data;

    public static <T> BaseResponse<T> fail() {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setCode(ERROR_CODE);
        return baseResponse;
    }

    public static <T> BaseResponse<T> fail(String errorMessage) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setCode(ERROR_CODE);
        baseResponse.setMessage(errorMessage);
        return baseResponse;
    }

    public static <T> BaseResponse<T> success(T data) {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setData(data);
        baseResponse.setMessage(SUCCESS_MESSAGE);
        return baseResponse;
    }

    public static <T> BaseResponse<T> success() {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setMessage(SUCCESS_MESSAGE);
        return baseResponse;
    }
}
