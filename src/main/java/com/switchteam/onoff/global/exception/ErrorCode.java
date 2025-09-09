package com.switchteam.onoff.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    BAD_REQUEST(1001, "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1002, "인증되지 않은 사용자입니다.", HttpStatus.UNAUTHORIZED);

    private final int code;
    private final String message;
    private final HttpStatus status;
}

