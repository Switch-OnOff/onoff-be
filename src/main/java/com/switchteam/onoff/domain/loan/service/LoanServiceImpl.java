package com.switchteam.onoff.domain.loan.service;

import com.switchteam.onoff.domain.loan.domain.Loan;
import com.switchteam.onoff.domain.loan.repository.LoanRepository;
import com.switchteam.onoff.global.exception.CustomException;
import com.switchteam.onoff.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    @Override
    public List<Map<String, Object>> getTop5LoanNames() {
        return loanRepository.findTop5ByOrderByLoanNameAsc()
                .stream()
                .map(l -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("loanId", l.getLoanId());
                    map.put("loanName", l.getLoanName());
                    return map;
                })
                .toList();
    }


    @Override
    public List<Loan> filterLoans(String eligibleGroup, String loanType, String interestType, String repaymentMethod) {
        return loanRepository.searchLoansByFilters(eligibleGroup, loanType, interestType, repaymentMethod);
    }

    @Override
    public Loan getLoanById(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new CustomException(ErrorCode.LOAN_NOT_FOUND));
    }

}