package com.challenge.gladybackend.entry.controller;

import com.challenge.gladybackend.data.dto.EmployeeDTO;
import com.challenge.gladybackend.data.entity.Employee;
import com.challenge.gladybackend.data.request.CreateEmployeeRequest;
import com.challenge.gladybackend.data.view.EmployeeBalanceView;
import com.challenge.gladybackend.data.view.ErrorView;
import com.challenge.gladybackend.entry.validator.CreateEmployeeValidator;
import com.challenge.gladybackend.entry.validator.IdValidator;
import com.challenge.gladybackend.exception.AppNotFoundException;
import com.challenge.gladybackend.exception.AppValidatorException;
import com.challenge.gladybackend.helper.CompanyMaker;
import com.challenge.gladybackend.helper.EmployeeMaker;
import com.challenge.gladybackend.service.DepositService;
import com.challenge.gladybackend.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static com.challenge.gladybackend.helper.TestUtils.toJson;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class EmployeeControllerTest {

    @Value("${app.api.root-path}")
    private String rootPath;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private DepositService depositService;

    @MockBean
    private CreateEmployeeValidator createEmployeeValidator;

    @MockBean
    private IdValidator idValidator;

    @Test
    public void contextLoad() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void getSuccessTest() throws Exception {
        Employee employee = EmployeeMaker.makeEmployee();
        EmployeeDTO expected = EmployeeMaker.makeEmployeeDTO();

        Mockito.when(employeeService.get(EmployeeMaker.EMPLOYEE_ID)).thenReturn(employee);

        mockMvc.perform(get(rootPath + "/employees/" + EmployeeMaker.EMPLOYEE_ID)).andExpect(status().isOk())
            .andExpect(content().json(toJson(expected)));
    }

    @Test
    public void getIdInvalidTest() throws Exception {
        ErrorView errorView = EmployeeMaker.makeErrorView();

        Mockito.doThrow(new AppValidatorException(EmployeeMaker.EMPLOYEE_BAD_REQUEST, HttpStatus.BAD_REQUEST)).when(idValidator)
            .isValidOrThrow(EmployeeMaker.EMPLOYEE_ID);

        mockMvc.perform(get(rootPath + "/employees/" + CompanyMaker.COMPANY_ID)).andExpect(status().isBadRequest())
            .andExpect(content().json(toJson(errorView)));
    }

    @Test
    public void getNotFoundTest() throws Exception {
        ErrorView errorView = EmployeeMaker.makeErrorView();
        errorView.setMessage(EmployeeMaker.EMPLOYEE_NOT_FOUND);

        Mockito.when(employeeService.get(EmployeeMaker.EMPLOYEE_ID))
            .thenThrow(new AppNotFoundException(EmployeeMaker.EMPLOYEE_NOT_FOUND, HttpStatus.BAD_REQUEST));

        mockMvc.perform(get(rootPath + "/employees/" + EmployeeMaker.EMPLOYEE_ID)).andExpect(status().isBadRequest())
            .andExpect(content().json(toJson(errorView)));
    }

    @Test
    public void createSuccessTest() throws Exception {
        CreateEmployeeRequest request = EmployeeMaker.makeCreateEmployeeRequest();
        Employee employee = EmployeeMaker.makeEmployee();
        EmployeeDTO expected = EmployeeMaker.makeEmployeeDTO();

        Mockito.when(employeeService.create(request)).thenReturn(employee);

        mockMvc.perform(post(rootPath + "/employees/").contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(status().isOk()).andExpect(content().json(toJson(expected)));
    }

    @Test
    public void createInvalidRequestTest() throws Exception {
        CreateEmployeeRequest request = EmployeeMaker.makeCreateEmployeeRequest();
        ErrorView errorView = EmployeeMaker.makeErrorView();

        Mockito.doThrow(new AppValidatorException(EmployeeMaker.EMPLOYEE_BAD_REQUEST, HttpStatus.BAD_REQUEST)).when(createEmployeeValidator)
            .isValidOrThrow(request);

        mockMvc.perform(post(rootPath + "/employees/").contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(status().isBadRequest()).andExpect(content().json(toJson(errorView)));
    }

    @Test
    public void createNotFoundTest() throws Exception {
        CreateEmployeeRequest request = EmployeeMaker.makeCreateEmployeeRequest();
        ErrorView errorView = EmployeeMaker.makeErrorView();
        errorView.setMessage(EmployeeMaker.EMPLOYEE_NOT_FOUND);

        Mockito.when(employeeService.create(request))
            .thenThrow(new AppNotFoundException(EmployeeMaker.EMPLOYEE_NOT_FOUND, HttpStatus.BAD_REQUEST));

        mockMvc.perform(post(rootPath + "/employees/").contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(status().isBadRequest()).andExpect(content().json(toJson(errorView)));
    }

    @Test
    public void getEmployeeBalanceSuccessTest() throws Exception {
        EmployeeBalanceView expected = EmployeeMaker.makeEmployeeBalanceView();

        Mockito.when(depositService.getEmployeeBalance(EmployeeMaker.EMPLOYEE_ID)).thenReturn(expected);

        mockMvc.perform(get(rootPath + "/employees/" + EmployeeMaker.EMPLOYEE_ID + "/balance")).andExpect(status().isOk())
            .andExpect(content().json(toJson(expected)));
    }

    @Test
    public void getEmployeeBalanceFailTest() throws Exception {
        ErrorView errorView = EmployeeMaker.makeErrorView();

        Mockito.doThrow(new AppValidatorException(EmployeeMaker.EMPLOYEE_BAD_REQUEST, HttpStatus.BAD_REQUEST)).when(idValidator)
            .isValidOrThrow(EmployeeMaker.EMPLOYEE_ID);

        mockMvc.perform(get(rootPath + "/employees/" + EmployeeMaker.EMPLOYEE_ID + "/balance")).andExpect(status().isBadRequest())
            .andExpect(content().json(toJson(errorView)));
    }

}
