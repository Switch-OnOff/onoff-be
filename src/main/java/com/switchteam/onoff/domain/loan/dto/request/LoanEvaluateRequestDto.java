package com.switchteam.onoff.domain.loan.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanEvaluateRequestDto {

    @NotNull(message = "대출 금액은 필수 입력값입니다.")
    @Min(value = 1, message = "대출 금액은 1원 이상이어야 합니다.")
    private int creditScore; // 신용점수

    @NotNull(message = "상환 개월 수는 필수 입력값입니다.")
    @Min(value = 1, message = "상환 개월 수는 1개월 이상이어야 합니다.")
    private Long loanAmount; // 희망 대출금액

    //이거 validation 기준 애매...
    @NotNull(message = "상환 개월 수는 필수 입력값입니다.")
    @Min(value = 1, message = "상환 개월 수는 1개월 이상이어야 합니다.")
    private int repaymentMonths; // 상환기간(개월)

    @NotNull(message = "소득은 필수 입력값입니다.")
    @Min(value = 1, message = "소득은 1원 이상이어야 합니다.")
    private Double income; // 사용자의 월소득

    @NotNull(message = "신용점수는 필수 입력값입니다.")
    @Min(value = 0) @Max(value = 1000)
    private Double repayRatio; // 상환가능 비율 (예: 0.3 = 소득의 30%)
}
