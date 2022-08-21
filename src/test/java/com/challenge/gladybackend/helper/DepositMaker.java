package com.challenge.gladybackend.helper;

import com.challenge.gladybackend.data.dto.DepositDTO;
import com.challenge.gladybackend.data.entity.Deposit;
import com.challenge.gladybackend.data.request.CreateDepositRequest;
import com.challenge.gladybackend.data.view.ErrorView;
import org.springframework.http.HttpStatus;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;

public class DepositMaker {

    public static final int DEPOSIT_ID = 1;
    public static final int DEPOSIT_AMOUNT = 80;
    public static final Date DEPOSIT_DATE = Date.from(Clock.fixed(Instant.parse("2000-08-21T18:00:00Z"), ZoneOffset.UTC).instant());
    public static final Date DEPOSIT_EXPIRED = Date.from(Clock.fixed(Instant.parse("2001-08-21T00:00:00Z"), ZoneOffset.UTC).instant());
    public static final String DEPOSIT_TYPE = "GIFT";
    public static final String DEPOSIT_BAD_REQUEST = "BAD REQUEST";
    public static final String DEPOSIT_NOT_FOUND = "NOT FOUND";

    public static Deposit makeDeposit() {
        Deposit deposit = new Deposit();
        deposit.setId(DEPOSIT_ID);
        deposit.setAmount(DEPOSIT_AMOUNT);
        deposit.setDate(DEPOSIT_DATE);
        deposit.setExpire(DEPOSIT_EXPIRED);
        deposit.setType(DEPOSIT_TYPE);
        deposit.setEmployee(EmployeeMaker.makeEmployee());
        return deposit;
    }

    public static DepositDTO makeDepositDTO() {
        return new DepositDTO(DEPOSIT_ID, DEPOSIT_AMOUNT, DEPOSIT_DATE, DEPOSIT_EXPIRED, DEPOSIT_TYPE, EmployeeMaker.EMPLOYEE_ID);
    }

    public static CreateDepositRequest makeCreateDepositRequest() {
        CreateDepositRequest request = new CreateDepositRequest();
        request.setEmployeeId(EmployeeMaker.EMPLOYEE_ID);
        request.setType(DEPOSIT_TYPE);
        request.setAmount(DEPOSIT_AMOUNT);
        return request;
    }

    public static ErrorView makeErrorView() {
        return new ErrorView(HttpStatus.BAD_REQUEST.value(), DEPOSIT_BAD_REQUEST, Collections.emptyList());
    }

}
