package com.sarthak.Loan.service.consumer;

import com.sarthak.Loan.constants.LoansConstants;
import com.sarthak.Loan.entity.Loans;
import com.sarthak.Loan.exception.LoanAlreadyExistsException;
import com.sarthak.Loan.repository.LoansRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class AccountEventListener {

    private final LoansRepository loansRepository;

    @KafkaListener(
            topics = "account-created-topic",
            groupId = "loans-group"
    )
    @Transactional
    public void handleAccountCreated(com.sarthak.Loan.dto.event.AccountCreateEvent event) {
        Optional<Loans> existingLoan =
                loansRepository.findByMobileNumber(event.getMobileNumber());

        if(existingLoan.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber "+event.getMobileNumber());
        }

        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(event.getMobileNumber());
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);

        loansRepository.save(newLoan);
    }

}
