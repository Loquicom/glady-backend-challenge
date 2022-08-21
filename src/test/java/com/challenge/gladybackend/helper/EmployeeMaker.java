package com.challenge.gladybackend.helper;

import com.challenge.gladybackend.data.dto.EmployeeDTO;
import com.challenge.gladybackend.data.entity.Employee;
import com.challenge.gladybackend.data.request.CreateEmployeeRequest;
import com.challenge.gladybackend.data.view.EmployeeBalanceView;
import com.challenge.gladybackend.data.view.ErrorView;
import org.springframework.http.HttpStatus;

import java.util.Collections;

public class EmployeeMaker {

    public static final int EMPLOYEE_ID = 1;
    public static final String EMPLOYEE_FIRSTNAME = "firstname";
    public static final String EMPLOYEE_LASTNAME = "lastname";
    public static final int EMPLOYEE_BALANCE = 88;
    public static final String EMPLOYEE_BAD_REQUEST = "BAD REQUEST";
    public static final String EMPLOYEE_NOT_FOUND = "NOT FOUND";

    public static Employee makeEmployee() {
        Employee employee = new Employee();
        employee.setId(EMPLOYEE_ID);
        employee.setFirstname(EMPLOYEE_FIRSTNAME);
        employee.setLastname(EMPLOYEE_LASTNAME);
        employee.setCompany(CompanyMaker.makeCompany());
        return employee;
    }

    public static EmployeeDTO makeEmployeeDTO() {
        return new EmployeeDTO(EMPLOYEE_ID, EMPLOYEE_FIRSTNAME, EMPLOYEE_LASTNAME, CompanyMaker.COMPANY_ID);
    }

    public static CreateEmployeeRequest makeCreateEmployeeRequest() {
        CreateEmployeeRequest createEmployeeRequest = new CreateEmployeeRequest();
        createEmployeeRequest.setFirstname(EMPLOYEE_FIRSTNAME);
        createEmployeeRequest.setLastname(EMPLOYEE_LASTNAME);
        createEmployeeRequest.setCompanyId(CompanyMaker.COMPANY_ID);
        return createEmployeeRequest;
    }

    public static ErrorView makeErrorView() {
        return new ErrorView(HttpStatus.BAD_REQUEST.value(), EMPLOYEE_BAD_REQUEST, Collections.emptyList());
    }

    public static EmployeeBalanceView makeEmployeeBalanceView() {
        return new EmployeeBalanceView(EMPLOYEE_ID, EMPLOYEE_FIRSTNAME, EMPLOYEE_LASTNAME, EMPLOYEE_BALANCE, CompanyMaker.COMPANY_ID);
    }

}
