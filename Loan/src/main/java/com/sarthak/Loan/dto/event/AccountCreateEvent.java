package com.sarthak.Loan.dto.event;

import lombok.Data;

@Data
public class AccountCreateEvent {
    private Long accountNumber;
    private String accountType;
    private String customerName;
    private String mobileNumber;
    private String email;

    public AccountCreateEvent() {}

    public AccountCreateEvent(Long accountNumber,
                              String accountType,
                              String customerName,
                              String mobileNumber,
                              String email) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
        this.email = email;
    }

}