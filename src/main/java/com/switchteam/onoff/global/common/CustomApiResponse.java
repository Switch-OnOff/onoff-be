package com.switchteam.onoff.global.common;

import com.switchteam.onoff.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CustomApiResponse<T> {
    private final boolean success;
    private final int code;
    private final String message;
    private final T data;

    private CustomApiResponse(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 성공 응답
    public static <T> CustomApiResponse<T> success(SuccessCode successCode, T data) {
        return new CustomApiResponse<>(true, successCode.getCode(), successCode.getMessage(), data);
    }

    public static <T> CustomApiResponse<T> success(SuccessCode successCode) {
        return new CustomApiResponse<>(true, successCode.getCode(), successCode.getMessage(), null);
    }

    public static <T> CustomApiResponse<T> error(ErrorCode errorCode) {
        return new CustomApiResponse<>(false, errorCode.getCode(), errorCode.getMessage(), null);
    }
}