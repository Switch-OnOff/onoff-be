package com.switchteam.onoff.domain.loan.controller;

import com.switchteam.onoff.domain.loan.domain.Loan;
import com.switchteam.onoff.domain.loan.dto.request.LoanEvaluateRequestDto;
import com.switchteam.onoff.domain.loan.dto.response.LoanEvaluateResponseDto;
import com.switchteam.onoff.domain.loan.service.LoanService;
import com.switchteam.onoff.global.common.CustomApiResponse;
import com.switchteam.onoff.global.common.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/api/loan/{loanId}")
    @Operation(summary = "대출 상세 조회", description = "loanId로 대출의 모든 정보를 조회하는 API")
    public ResponseEntity<CustomApiResponse<Loan>> getLoanById(@PathVariable Long loanId) {
        Loan loan = loanService.getLoanById(loanId);
        return ResponseEntity.ok(
                CustomApiResponse.success(SuccessCode.LOAN_DETAIL_SUCCESS, loan)
        );
    }

    @PostMapping("/api/loan/{loanId}/evaluate")
    @Operation(summary = "특정 대출 신호등 평가", description = "loanId에 해당하는 대출상품을 사용자 조건으로 평가")
    public ResponseEntity<LoanEvaluateResponseDto> evaluateLoan(
            @PathVariable Long loanId,
            @RequestBody LoanEvaluateRequestDto loanEvaluateRequestDto) {

        LoanEvaluateResponseDto result = loanService.evaluateLoan(loanId, loanEvaluateRequestDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/loan/search")
    @Operation(summary = "대출 이름 검색", description = "loan_name을 키워드로 검색합니다")
    public ResponseEntity<CustomApiResponse<List<Loan>>> searchLoans(@RequestParam String keyword) {
        List<Loan> searchedLoans = loanService.searchByLoanName(keyword);
        return ResponseEntity.ok(
                CustomApiResponse.success(SuccessCode.LOAN_SEARCH_SUCCESS, searchedLoans)
        );
    }


}
