package com.challenge.gladybackend.helper;

import com.challenge.gladybackend.data.entity.Company;
import com.challenge.gladybackend.data.request.CreateCompanyRequest;
import com.challenge.gladybackend.data.request.CreditCompanyRequest;
import com.challenge.gladybackend.data.view.ErrorView;
import org.springframework.http.HttpStatus;

import java.util.Collections;

public class CompanyMaker {

    public static final int COMPANY_ID = 1;
    public static final String COMPANY_NAME = "Test company";
    public static final int COMPANY_BALANCE = 88;
    public static final String COMPANY_NOT_FOUND = "NOT FOUND";
    public static final String COMPANY_BAD_REQUEST = "BAD REQUEST";

    public static Company makeCompany() {
        Company company = new Company();
        company.setId(COMPANY_ID);
        company.setName(COMPANY_NAME);
        company.setBalance(COMPANY_BALANCE);
        return company;
    }

    public static CreateCompanyRequest makeCreateCompanyRequest() {
        CreateCompanyRequest request = new CreateCompanyRequest();
        request.setName(COMPANY_NAME);
        request.setBalance(COMPANY_BALANCE);
        return request;
    }

    public static CreditCompanyRequest makeCreditCompanyRequest() {
        CreditCompanyRequest request = new CreditCompanyRequest();
        request.setAmount(COMPANY_BALANCE);
        return request;
    }

    public static ErrorView makeErrorView() {
        return new ErrorView(HttpStatus.BAD_REQUEST.value(), CompanyMaker.COMPANY_BAD_REQUEST, Collections.emptyList());
    }

}
