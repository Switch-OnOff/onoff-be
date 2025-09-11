package com.switchteam.onoff.domain.loan.service;

import com.switchteam.onoff.domain.loan.domain.Loan;
import com.switchteam.onoff.domain.loan.dto.request.LoanEvaluateRequestDto;
import com.switchteam.onoff.domain.loan.dto.response.LoanEvaluateResponseDto;

import java.util.List;
import java.util.Map;

public interface LoanService {

    List<Map<String, Object>> getTop5LoanNames();

    List<Loan> filterLoans(String eligibleGroup, String loanType, String interestType, String repaymentMethod);

    Loan getLoanById(Long loanId);

    LoanEvaluateResponseDto evaluateLoan(Long loanId, LoanEvaluateRequestDto request);

    List<Loan> searchByLoanName(String keyword);
}
