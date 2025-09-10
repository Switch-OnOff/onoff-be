package com.switchteam.onoff.domain.loan.controller;

import com.switchteam.onoff.domain.loan.domain.Loan;
import com.switchteam.onoff.domain.loan.service.LoanService;
import com.switchteam.onoff.global.common.CustomApiResponse;
import com.switchteam.onoff.global.common.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Tag(name = "대출 관련 API", description = "대출 관련 API입니다")
@RestController
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @GetMapping("/api/loan/top5")
    @Operation(summary = "인기 대출", description = "TOP5 대출 보여줄때 사용하는 API")
    public ResponseEntity<CustomApiResponse<List<Map<String, Object>>>> getTop5Loans() {
        List<Map<String, Object>> loanNames = loanService.getTop5LoanNames();
        return ResponseEntity.ok(CustomApiResponse.success(
                SuccessCode.GRANT_TOP5_FETCH_SUCCESS, loanNames));
    }

    @GetMapping("/api/loan/filter")
    @Operation(summary = "대출 조건 필터링", description = "가입대상, 담보여부, 금리방식, 상환방식에 맞게 필터링합니다")
    public ResponseEntity<CustomApiResponse<List<Loan>>> filterLoans(
            @RequestParam(required = false) String eligibleGroup,
            @RequestParam(required = false) String loanType,
            @RequestParam(required = false) String interestType,
            @RequestParam(required = false) String repaymentMethod
    ) {
        List<Loan> filteredLoans = loanService.filterLoans(eligibleGroup, loanType, interestType, repaymentMethod);
        return ResponseEntity.ok(
                CustomApiResponse.success(SuccessCode.LOAN_FILTER_SUCCESS, filteredLoans)
        );
    }
}
