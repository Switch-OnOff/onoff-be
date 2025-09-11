package com.switchteam.onoff.domain.loan.service;

import com.switchteam.onoff.domain.loan.domain.Loan;
import com.switchteam.onoff.domain.loan.dto.request.LoanEvaluateRequestDto;
import com.switchteam.onoff.domain.loan.dto.response.LoanEvaluateResponseDto;
import com.switchteam.onoff.domain.loan.repository.LoanRepository;
import com.switchteam.onoff.global.exception.CustomException;
import com.switchteam.onoff.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
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


    @Override
    public LoanEvaluateResponseDto evaluateLoan(Long loanId, LoanEvaluateRequestDto loanEvaluateRequestDto) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new CustomException(ErrorCode.LOAN_NOT_FOUND));

        //연이율 결정
        double annualRate = resolveAnnualRate(loan);

        //하드코딩(,가 여러개인 경우 일단 첫번째 가져오도록함...)
        String repaymentMethod = firstMethod(loan.getRepaymentMethod());
        log.info("선택된 상환방식={}", repaymentMethod);

        log.info("대출 평가 시작 loanId={}, loanName={}, company={}, annualRate={}%, 선택된 상환방식={}, 요청대출금액={}",
                loan.getLoanId(),
                loan.getLoanName(),
                loan.getLoanCompany(),
                annualRate,
                repaymentMethod,
                loanEvaluateRequestDto.getLoanAmount()
        );

        //월 상환액 계산(실제로 매달 얼마를 내야하는지)
        double monthlyPayment = calcMonthlyPayment(
                loanEvaluateRequestDto.getLoanAmount(), annualRate, loanEvaluateRequestDto.getRepaymentMonths(), repaymentMethod);

        int score = baseScore(loan, annualRate);
        score = applyAdjustments(score, loan, repaymentMethod, monthlyPayment, loanEvaluateRequestDto);

        String grade = (score >= 80) ? "Green" : (score >= 60) ? "Yellow" : "Red";

        log.info("대출 평가 완료 loanId={}, loanName={}, 최종 score={}, grade={}, monthlyPayment={}",
                loan.getLoanId(),
                loan.getLoanName(),
                score,
                grade,
                Math.round(monthlyPayment)
        );

        return LoanEvaluateResponseDto.builder()
                .loanId(loan.getLoanId())
                .loanName(loan.getLoanName())
                .usedRepaymentMethod(repaymentMethod)
                .monthlyPayment(Math.round(monthlyPayment))
                .score(score)
                .grade(grade)
                .build();
    }


    private double resolveAnnualRate(Loan loan) {
        // avgInterestRate가 null 또는 0 이하라면 무효 → min/max 사용
        if (loan.getAvgInterestRate() != null && loan.getAvgInterestRate() > 0) {
            return loan.getAvgInterestRate();
        }
        if (loan.getMinInterestRate() != null && loan.getMaxInterestRate() != null) {
            return (loan.getMinInterestRate() + loan.getMaxInterestRate()) / 2.0;
        }
        throw new CustomException(ErrorCode.INVALID_REQUEST);
    }


    // 상환방식이 여러개인 경우 첫번째 것을 사용
    private String firstMethod(String repaymentMethod) {
        if (repaymentMethod == null || repaymentMethod.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_REPAYMENT_METHOD);
        }
        return repaymentMethod.split(",")[0].trim();
    }

    //담보대출과 신용대출 두개인 경우에는 평균 점수
    private int baseScore(Loan loan, double annualRate) {
        String loanType = loan.getLoanType();
        int score = 50; // 기본값
        int count = 0;
        int total = 0;

        if (loanType != null && loanType.contains("담보대출")) {
            count++;
            if (annualRate <= 6)
                total += 90;
            else if (annualRate <= 10)
                total += 70;
            else
                total += 50;
        }

        if (loanType != null && loanType.contains("신용대출")) {
            count++;
            if (annualRate <= 8)
                total += 90;
            else if (annualRate <= 12)
                total += 70;
            else
                total += 50;
        }

        if (count > 0)
            score = total / count; // 평균 점수

        return score;
    }


    private int applyAdjustments(int score, Loan loan, String method,
                                 double monthlyPayment, LoanEvaluateRequestDto loanEvaluateRequestDto) {
        // 1) 상환방식 리스크 반영
        //리스크가 더 큰 데이터는 -10점 주기
        //repayment_method, interest_type, eligible_group
        if (method.contains("만기일시")) score -= 10;
        if (loan.getInterestType() != null && loan.getInterestType().contains("변동")) score -= 10;
        if (loan.getEligibleGroup() != null && loan.getEligibleGroup().contains("기타")) score -= 10;

        // 2) 소득 대비 상환능력 체크
        //월 상환액이 사용자의 최대 감당 가능 금액보다 크면 -20점
        double maxAffordable = loanEvaluateRequestDto.getIncome() * loanEvaluateRequestDto.getRepayRatio();
        //monthlyPayment -> 월 상한액
        //maxAffordable -> 월 소득 * 상환 가능 비율 (최대 감당 가능 금액)
        if (monthlyPayment > maxAffordable)
            score -= 20;

        // 3) 신용점수 반영
        int credit = loanEvaluateRequestDto.getCreditScore();
        if (credit < 500) {
            return 0; // 컷오프 → 바로 Red
        } else if (credit < 600) {
            score -= 20; // 낮은 점수 → 감점
        } else if (credit >= 800) {
            score += 10; // 아주 우수 → 가산점
        } else if (credit >= 700) {
            score += 5;  // 양호 → 가산점
        }

        return Math.max(score, 0); // 음수 방지
    }

    //식 공식보고 지피티가 짜줌,,,(정확하겠지...라고 생각하고 일단 넘기기)
    private double calcMonthlyPayment(Long principal, double annualRate, int months, String method) {
        if (principal == null || principal <= 0) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }
        if (annualRate <= 0) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }
        if (months <= 0) {
            throw new CustomException(ErrorCode.INVALID_REQUEST);
        }
        if (method == null || method.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_REPAYMENT_METHOD);
        }

        double r = (annualRate / 100.0) / 12.0; // 월 이자율

        if (method.contains("원리금")) { // 원리금균등
            double pow = Math.pow(1 + r, months);
            return principal * r * pow / (pow - 1);
        } else if (method.contains("원금")) { // 원금균등 (첫 달 기준)
            double principalPart = principal / (double) months;
            double interestPart = principal * r;
            return principalPart + interestPart;
        } else if (method.contains("만기일시")) { // 만기일시
            return principal * r;
        }
        return 0.0;
    }

    @Override
    public List<Loan> searchByLoanName(String keyword) {
        return loanRepository.searchByLoanName(keyword);
    }

}

