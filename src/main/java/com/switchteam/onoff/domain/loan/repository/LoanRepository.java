package com.switchteam.onoff.domain.loan.repository;

import com.switchteam.onoff.domain.grants.domain.Grants;
import com.switchteam.onoff.domain.loan.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findTop5ByOrderByLoanNameAsc();

    @Query("SELECT l FROM Loan l " +
            "WHERE (:eligibleGroup IS NULL OR l.eligibleGroup LIKE %:eligibleGroup%) " +
            "  AND (:loanType IS NULL OR l.loanType LIKE %:loanType%) " +
            "  AND (:interestType IS NULL OR l.interestType LIKE %:interestType%) " +
            "  AND (:repaymentMethod IS NULL OR l.repaymentMethod LIKE %:repaymentMethod%)")
    List<Loan> searchLoansByFilters(
            @Param("eligibleGroup") String eligibleGroup,
            @Param("loanType") String loanType,
            @Param("interestType") String interestType,
            @Param("repaymentMethod") String repaymentMethod
    );
}
