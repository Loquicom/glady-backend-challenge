package com.challenge.gladybackend.service;

import com.challenge.gladybackend.data.entity.Company;
import com.challenge.gladybackend.data.request.CreateCompanyRequest;
import com.challenge.gladybackend.data.request.CreditCompanyRequest;
import com.challenge.gladybackend.exception.AppNotFoundException;
import com.challenge.gladybackend.helper.CompanyMaker;
import com.challenge.gladybackend.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class CompanyServiceTest {

    @Autowired
    private CompanyService service;

    @MockBean
    private CompanyRepository companyRepository;

    @Test
    public void contextLoad() {
        assertThat(service).isNotNull();
    }

    @Test
    public void getSuccessTest() throws AppNotFoundException {
        Company company = CompanyMaker.makeCompany();

        Mockito.when(companyRepository.findById(CompanyMaker.COMPANY_ID)).thenReturn(Optional.of(company));

        Company result = service.get(CompanyMaker.COMPANY_ID);
        assertThat(result).isEqualTo(company);
    }

    @Test
    public void getFailTest() {
        Mockito.when(companyRepository.findById(CompanyMaker.COMPANY_ID)).thenReturn(Optional.empty());

        AppNotFoundException ex = assertThrows(AppNotFoundException.class, () -> service.get(CompanyMaker.COMPANY_ID));
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createTest() {
        CreateCompanyRequest request = CompanyMaker.makeCreateCompanyRequest();
        Company company = CompanyMaker.makeCompany();
        company.setId(0);
        Company expected = CompanyMaker.makeCompany();

        Mockito.when(companyRepository.save(company)).thenReturn(expected);

        Company result = service.create(request);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void creditSuccessTest() throws AppNotFoundException {
        CreditCompanyRequest request = CompanyMaker.makeCreditCompanyRequest();
        Company company = CompanyMaker.makeCompany();
        Company expected = CompanyMaker.makeCompany();
        expected.setBalance(expected.getBalance() + request.getAmount());

        Mockito.when(companyRepository.findById(CompanyMaker.COMPANY_ID)).thenReturn(Optional.of(company));
        Mockito.when(companyRepository.save(expected)).thenReturn(expected);

        Company result = service.credit(CompanyMaker.COMPANY_ID, request);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void creditFailTest() {
        CreditCompanyRequest request = CompanyMaker.makeCreditCompanyRequest();

        Mockito.when(companyRepository.findById(CompanyMaker.COMPANY_ID)).thenReturn(Optional.empty());

        AppNotFoundException ex = assertThrows(AppNotFoundException.class, () -> service.credit(CompanyMaker.COMPANY_ID, request));
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
