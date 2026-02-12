package org.sarthak.accounts.service.client;

import org.sarthak.accounts.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient{

    @Override
    public ResponseEntity<LoansDto> fetchLoanDetails(String correlationId, String mobileNumber) {
        LoansDto fallback = new LoansDto();
        fallback.setMobileNumber(mobileNumber);
        fallback.setLoanNumber("000000000000");
        fallback.setLoanType("Service Unavailable");
        fallback.setTotalLoan(0);
        fallback.setAmountPaid(0);
        fallback.setOutstandingAmount(0);

        return ResponseEntity.ok(fallback);
    }
}