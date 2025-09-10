package com.switchteam.onoff.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    BAD_REQUEST(1001, "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1002, "인증되지 않은 사용자입니다.", HttpStatus.UNAUTHORIZED),
    GRANT_NOT_FOUND(1003, "지원금 정보가 존재하지 않습니다.", HttpStatus.NOT_FOUND);

    private final int code;
    private final String message;
    private final HttpStatus status;
}

