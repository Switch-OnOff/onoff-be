package com.switchteam.onoff.domain.loan.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "loan")
@Getter
@NoArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long loanId;

    @Column(name = "loan_name", nullable = false, length = 255)
    private String loanName;

    @Column(name = "loan_company", nullable = false, length = 255)
    private String loanCompany;

    @Column(name = "eligible_group", nullable = false, length = 255)
    private String eligibleGroup;

    @Column(name = "loan_type", nullable = false, length = 100)
    private String loanType;

    @Column(name = "interest_type", nullable = false, length = 100)
    private String interestType;

    @Column(name = "repayment_method", nullable = false, length = 255)
    private String repaymentMethod;

    @Column(name = "min_interest_rate")
    private Double minInterestRate;

    @Column(name = "max_interest_rate")
    private Double maxInterestRate;

    @Column(name = "avg_interest_rate")
    private Double avgInterestRate;

    @Column(name = "requirements", columnDefinition = "TEXT")
    private String requirements;
}
