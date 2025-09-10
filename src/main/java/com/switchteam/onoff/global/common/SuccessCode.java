package com.switchteam.onoff.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SuccessCode {

    LOGIN_SUCCESS(301, "로그인 성공"),
    GRANT_TOP5_FETCH_SUCCESS(201, "지원금 TOP5 조회 성공"),
    GRANT_FILTER_SUCCESS(202, "지원금 필터링 성공"),
    GRANT_VALIDATE_SUCCESS(203, "지원금 받을 수 있는 여부 확인 성공"),
    GRANT_CHECK_SUCCESS(204, "지원금 받을 수 있는 경우 체크리스트 가져오기 성공"),
    FILTER_SUCCESS(205, "필터링 성공"),
    GRANT_SEARCH_SUCCESS(206, "지원금 검색 성공"),
    PROPERTY_CARD_DATA_SUCCESS(200, "카드 리스트 조회 성공"),
    PROPERTY_CARD_DATA_BY_ID_SUCCESS(200, "id로 카드 데이터 조회 성공"),
    GRANT_DETAIL_SUCCESS(207, "지원금 상세 조회 성공"),
    LOAN_FILTER_SUCCESS(208, "대출 필터링 성공"),
    LOAN_DETAIL_SUCCESS(209, "대출 상세 조회 성공");

    private final int code;
    private final String message;
}
