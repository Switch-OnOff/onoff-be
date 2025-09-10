package com.switchteam.onoff.domain.loan.service;

import com.switchteam.onoff.domain.loan.domain.Loan;

import java.util.List;
import java.util.Map;

public interface LoanService {

    List<Map<String, Object>> getTop5LoanNames();

    List<Loan> filterLoans(String eligibleGroup, String loanType, String interestType, String repaymentMethod);
}
