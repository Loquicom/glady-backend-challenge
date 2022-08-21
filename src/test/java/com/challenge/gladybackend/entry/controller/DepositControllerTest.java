package com.challenge.gladybackend.entry.controller;

import com.challenge.gladybackend.data.dto.DepositDTO;
import com.challenge.gladybackend.data.entity.Deposit;
import com.challenge.gladybackend.data.request.CreateDepositRequest;
import com.challenge.gladybackend.data.view.EmployeeBalanceView;
import com.challenge.gladybackend.data.view.ErrorView;
import com.challenge.gladybackend.entry.validator.CreateDepositValidator;
import com.challenge.gladybackend.entry.validator.IdValidator;
import com.challenge.gladybackend.exception.AppNotFoundException;
import com.challenge.gladybackend.exception.AppValidatorException;
import com.challenge.gladybackend.helper.DepositMaker;
import com.challenge.gladybackend.helper.EmployeeMaker;
import com.challenge.gladybackend.service.DepositService;
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
public class DepositControllerTest {

    @Value("${app.api.root-path}")
    private String rootPath;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepositService depositService;

    @MockBean
    private CreateDepositValidator createDepositValidator;

    @MockBean
    private IdValidator idValidator;

    @Test
    public void contextLoad() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void getSuccessTest() throws Exception {
        Deposit deposit = DepositMaker.makeDeposit();
        DepositDTO expected = DepositMaker.makeDepositDTO();

        Mockito.when(depositService.get(DepositMaker.DEPOSIT_ID)).thenReturn(deposit);

        mockMvc.perform(get(rootPath + "/deposits/" + DepositMaker.DEPOSIT_ID)).andExpect(status().isOk())
            .andExpect(content().json(toJson(expected)));
    }

    @Test
    public void getIdInvalidTest() throws Exception {
        ErrorView errorView = DepositMaker.makeErrorView();

        Mockito.doThrow(new AppValidatorException(DepositMaker.DEPOSIT_BAD_REQUEST, HttpStatus.BAD_REQUEST)).when(idValidator)
            .isValidOrThrow(DepositMaker.DEPOSIT_ID);

        mockMvc.perform(get(rootPath + "/deposits/" + DepositMaker.DEPOSIT_ID)).andExpect(status().isBadRequest())
            .andExpect(content().json(toJson(errorView)));
    }

    @Test
    public void getNotFoundTest() throws Exception {
        ErrorView errorView = DepositMaker.makeErrorView();
        errorView.setMessage(DepositMaker.DEPOSIT_NOT_FOUND);

        Mockito.when(depositService.get(DepositMaker.DEPOSIT_ID))
            .thenThrow(new AppNotFoundException(DepositMaker.DEPOSIT_NOT_FOUND, HttpStatus.BAD_REQUEST));

        mockMvc.perform(get(rootPath + "/deposits/" + DepositMaker.DEPOSIT_ID)).andExpect(status().isBadRequest())
            .andExpect(content().json(toJson(errorView)));
    }

    @Test
    public void createSuccessTest() throws Exception {
        CreateDepositRequest request = DepositMaker.makeCreateDepositRequest();
        Deposit deposit = DepositMaker.makeDeposit();
        DepositDTO expected = DepositMaker.makeDepositDTO();

        Mockito.when(depositService.create(request)).thenReturn(deposit);

        mockMvc.perform(post(rootPath + "/deposits/").contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(status().isOk()).andExpect(content().json(toJson(expected)));
    }

    @Test
    public void createInvalidRequestTest() throws Exception {
        CreateDepositRequest request = DepositMaker.makeCreateDepositRequest();
        ErrorView errorView = DepositMaker.makeErrorView();

        Mockito.doThrow(new AppValidatorException(DepositMaker.DEPOSIT_BAD_REQUEST, HttpStatus.BAD_REQUEST)).when(createDepositValidator)
            .isValidOrThrow(request);

        mockMvc.perform(post(rootPath + "/deposits/").contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(status().isBadRequest()).andExpect(content().json(toJson(errorView)));
    }

    @Test
    public void createNotFoundTest() throws Exception {
        CreateDepositRequest request = DepositMaker.makeCreateDepositRequest();
        ErrorView errorView = DepositMaker.makeErrorView();
        errorView.setMessage(DepositMaker.DEPOSIT_NOT_FOUND);

        Mockito.when(depositService.create(request))
            .thenThrow(new AppNotFoundException(DepositMaker.DEPOSIT_NOT_FOUND, HttpStatus.BAD_REQUEST));

        mockMvc.perform(post(rootPath + "/deposits/").contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(status().isBadRequest()).andExpect(content().json(toJson(errorView)));
    }

    @Test
    public void getEmployeeBalanceSuccessTest() throws Exception {
        EmployeeBalanceView expected = EmployeeMaker.makeEmployeeBalanceView();

        Mockito.when(depositService.getEmployeeBalance(EmployeeMaker.EMPLOYEE_ID)).thenReturn(expected);

        mockMvc.perform(get(rootPath + "/deposits/employees/" + EmployeeMaker.EMPLOYEE_ID + "/balance")).andExpect(status().isOk())
            .andExpect(content().json(toJson(expected)));
    }

    @Test
    public void getEmployeeBalanceFailTest() throws Exception {
        ErrorView errorView = DepositMaker.makeErrorView();

        Mockito.doThrow(new AppValidatorException(DepositMaker.DEPOSIT_BAD_REQUEST, HttpStatus.BAD_REQUEST)).when(idValidator)
            .isValidOrThrow(DepositMaker.DEPOSIT_ID);

        mockMvc.perform(get(rootPath + "/deposits/employees/" + EmployeeMaker.EMPLOYEE_ID + "/balance")).andExpect(status().isBadRequest())
            .andExpect(content().json(toJson(errorView)));
    }

}
