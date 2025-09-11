package com.switchteam.onoff.domain.loan.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoanEvaluateResponseDto {

    private Long loanId;
    private String loanName;
    private String usedRepaymentMethod; // 실제 평가에 사용된 상환방식(첫 번째 값)
    private double monthlyPayment;      // 월 상환액
    private int score;                  // 최종 점수
    private String grade;               // Green / Yellow / Red
}
