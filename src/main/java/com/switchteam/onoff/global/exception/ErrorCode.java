package com.switchteam.onoff.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    BAD_REQUEST(1001, "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1002, "인증되지 않은 사용자입니다.", HttpStatus.UNAUTHORIZED),
    GRANT_NOT_FOUND(1003, "지원금 정보가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    LOAN_NOT_FOUND(1004, "대출 정보가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    INVALID_REQUEST(1005, "유효하지 않은 요청입니다.", HttpStatus.BAD_REQUEST),
    INVALID_REPAYMENT_METHOD(1006, "유효하지 않은 상환방식입니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1004, "사용자 정보가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    PROPERTY_CREATE_ERROR(1005, "DB Insert 실패", HttpStatus.BAD_REQUEST),
    BNO_NOT_CORRECT(1006,"사업자번호 양식이 맞지 않습니다", HttpStatus.BAD_REQUEST),
    START_DATE_NOT_CORRECT(1007, "날짜 양식이 맞지 않습니다", HttpStatus.BAD_REQUEST),
    PNM_IS_EMPTY(1008, "사업자 이름은 필수입니다.", HttpStatus.NOT_FOUND),
    ODCLOUD_NON_OK_ERROR(1009, "응답이 정상이 아닙니다.", HttpStatus.BAD_REQUEST),
    ODCLOUD_CALL_API_ERROR(1010,"API 요청 실패", HttpStatus.INTERNAL_SERVER_ERROR),
    ODCLOUD_DATA_MISSING_ERROR(1011,"응답 데이터가 없습니다.", HttpStatus.NOT_FOUND),
    ODCLOUD_VALID_MISSING_ERROR(1012, "응답값이 없습니다", HttpStatus.NOT_FOUND),
    ODCLOUD_PARSING_ERROR(1013, "ODCloud 응답 파싱 실패", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXISTS(1014, "이미 존재하는 사용자입니다.", HttpStatus.CONFLICT),
    PASSWORD_MISMATCH(1015, "비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1016, "비밀번호가 올바르지 않습니다.", HttpStatus.UNAUTHORIZED),
    IMAGE_LIMIT_EXCEEDED(1017, "이미지는 최대 5장까지 업로드 가능합니다.", HttpStatus.BAD_REQUEST),
    POST_IMAGE_NOT_FOUND(1018, "해당 매물의 이미지를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    IMAGE_CONVERSION_FAILED(1019, "이미지 변환에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    CHATROOM_NOT_FOUND(1020, "채팅방을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    IMAGE_TRANSPORT_ERROR(1021, "이미지 전송에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    OPENAI_ANALYSIS_ERROR(1022, "OpenAI 분석에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatus status;
}

