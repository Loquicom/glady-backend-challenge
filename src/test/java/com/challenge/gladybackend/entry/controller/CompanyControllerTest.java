package com.challenge.gladybackend.entry.controller;

import com.challenge.gladybackend.data.entity.Company;
import com.challenge.gladybackend.data.request.CreateCompanyRequest;
import com.challenge.gladybackend.data.request.CreditCompanyRequest;
import com.challenge.gladybackend.data.view.ErrorView;
import com.challenge.gladybackend.entry.validator.CreateCompanyValidator;
import com.challenge.gladybackend.entry.validator.CreditCompanyValidator;
import com.challenge.gladybackend.entry.validator.IdValidator;
import com.challenge.gladybackend.exception.AppNotFoundException;
import com.challenge.gladybackend.exception.AppValidatorException;
import com.challenge.gladybackend.helper.CompanyMaker;
import com.challenge.gladybackend.service.CompanyService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class CompanyControllerTest {

    @Value("${app.api.root-path}")
    private String rootPath;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @MockBean
    private CreateCompanyValidator createCompanyValidator;

    @MockBean
    private IdValidator idValidator;

    @MockBean
    private CreditCompanyValidator creditCompanyValidator;

    @Test
    public void contextLoad() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void getSuccessTest() throws Exception {
        Company company = CompanyMaker.makeCompany();

        Mockito.when(companyService.get(CompanyMaker.COMPANY_ID)).thenReturn(company);

        mockMvc.perform(get(rootPath + "/companies/" + CompanyMaker.COMPANY_ID)).andExpect(status().isOk())
            .andExpect(content().json(toJson(company)));
    }

    @Test
    public void getIdInvalidIdTest() throws Exception {
        ErrorView errorView = CompanyMaker.makeErrorView();

        Mockito.doThrow(new AppValidatorException(CompanyMaker.COMPANY_BAD_REQUEST, HttpStatus.BAD_REQUEST)).when(idValidator)
            .isValidOrThrow(CompanyMaker.COMPANY_ID);

        mockMvc.perform(get(rootPath + "/companies/" + CompanyMaker.COMPANY_ID)).andExpect(status().isBadRequest())
            .andExpect(content().json(toJson(errorView)));
    }

    @Test
    public void getNotFoundTest() throws Exception {
        ErrorView errorView = CompanyMaker.makeErrorView();
        errorView.setMessage(CompanyMaker.COMPANY_NOT_FOUND);

        Mockito.when(companyService.get(CompanyMaker.COMPANY_ID))
            .thenThrow(new AppNotFoundException(CompanyMaker.COMPANY_NOT_FOUND, HttpStatus.BAD_REQUEST));

        mockMvc.perform(get(rootPath + "/companies/" + CompanyMaker.COMPANY_ID)).andExpect(status().isBadRequest())
            .andExpect(content().json(toJson(errorView)));
    }

    @Test
    public void createSuccessTest() throws Exception {
        CreateCompanyRequest request = CompanyMaker.makeCreateCompanyRequest();
        Company company = CompanyMaker.makeCompany();

        Mockito.when(companyService.create(request)).thenReturn(company);

        mockMvc.perform(post(rootPath + "/companies/").contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(status().isOk()).andExpect(content().json(toJson(company)));
    }

    @Test
    public void createNoRequestTest() throws Exception {
        mockMvc.perform(post(rootPath + "/companies/")).andExpect(status().isBadRequest());
    }

    @Test
    public void createInvalidTest() throws Exception {
        CreateCompanyRequest request = CompanyMaker.makeCreateCompanyRequest();
        ErrorView errorView = CompanyMaker.makeErrorView();

        Mockito.doThrow(new AppValidatorException(CompanyMaker.COMPANY_BAD_REQUEST, HttpStatus.BAD_REQUEST)).when(createCompanyValidator)
            .isValidOrThrow(request);

        mockMvc.perform(post(rootPath + "/companies/").contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(status().isBadRequest()).andExpect(content().json(toJson(errorView)));
    }

    @Test
    public void creditSuccessTest() throws Exception {
        CreditCompanyRequest request = CompanyMaker.makeCreditCompanyRequest();
        Company expected = CompanyMaker.makeCompany();
        expected.setBalance(expected.getBalance() + request.getAmount());

        Mockito.when(companyService.credit(CompanyMaker.COMPANY_ID, request)).thenReturn(expected);

        mockMvc.perform(put(rootPath + "/companies/" + CompanyMaker.COMPANY_ID + "/credit").contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request))).andExpect(status().isOk()).andExpect(content().json(toJson(expected)));
    }

    @Test
    public void creditNotFoundTest() throws Exception {
        CreditCompanyRequest request = CompanyMaker.makeCreditCompanyRequest();
        ErrorView errorView = CompanyMaker.makeErrorView();
        errorView.setMessage(CompanyMaker.COMPANY_NOT_FOUND);

        Mockito.when(companyService.credit(CompanyMaker.COMPANY_ID, request))
            .thenThrow(new AppNotFoundException(CompanyMaker.COMPANY_NOT_FOUND, HttpStatus.BAD_REQUEST));

        mockMvc.perform(put(rootPath + "/companies/" + CompanyMaker.COMPANY_ID + "/credit").contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request))).andExpect(status().isBadRequest()).andExpect(content().json(toJson(errorView)));
    }

    @Test
    public void creditInvalidIdTest() throws Exception {
        CreditCompanyRequest request = CompanyMaker.makeCreditCompanyRequest();
        ErrorView errorView = CompanyMaker.makeErrorView();

        Mockito.doThrow(new AppValidatorException(CompanyMaker.COMPANY_BAD_REQUEST, HttpStatus.BAD_REQUEST)).when(idValidator)
            .isValidOrThrow(CompanyMaker.COMPANY_ID);

        mockMvc.perform(put(rootPath + "/companies/" + CompanyMaker.COMPANY_ID + "/credit").contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request))).andExpect(status().isBadRequest()).andExpect(content().json(toJson(errorView)));
    }

    @Test
    public void creditInvalidTest() throws Exception {
        CreditCompanyRequest request = CompanyMaker.makeCreditCompanyRequest();

        Mockito.doThrow(new AppValidatorException(CompanyMaker.COMPANY_BAD_REQUEST, HttpStatus.BAD_REQUEST)).when(creditCompanyValidator)
            .isValidOrThrow(request);

        mockMvc.perform(put(rootPath + "/companies/" + CompanyMaker.COMPANY_ID + "/credit").contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request))).andExpect(status().isBadRequest());
    }

}
