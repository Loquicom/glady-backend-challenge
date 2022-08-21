package com.challenge.gladybackend.service;

import com.challenge.gladybackend.data.entity.Company;
import com.challenge.gladybackend.data.entity.Employee;
import com.challenge.gladybackend.data.request.CreateEmployeeRequest;
import com.challenge.gladybackend.exception.AppNotFoundException;
import com.challenge.gladybackend.helper.CompanyMaker;
import com.challenge.gladybackend.helper.EmployeeMaker;
import com.challenge.gladybackend.repository.EmployeeRepository;
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
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService service;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private CompanyService companyService;

    @Test
    public void contextLoad() {
        assertThat(service).isNotNull();
    }

    @Test
    public void getSuccessTest() throws AppNotFoundException {
        Employee employee = EmployeeMaker.makeEmployee();

        Mockito.when(employeeRepository.findById(EmployeeMaker.EMPLOYEE_ID)).thenReturn(Optional.of(employee));

        Employee result = service.get(EmployeeMaker.EMPLOYEE_ID);
        assertThat(result).isEqualTo(employee);
    }

    @Test
    public void getFailTest() {
        Mockito.when(employeeRepository.findById(EmployeeMaker.EMPLOYEE_ID)).thenReturn(Optional.empty());

        AppNotFoundException ex = assertThrows(AppNotFoundException.class, () -> service.get(EmployeeMaker.EMPLOYEE_ID));
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createSuccessTest() throws AppNotFoundException {
        CreateEmployeeRequest request = EmployeeMaker.makeCreateEmployeeRequest();
        Company company = CompanyMaker.makeCompany();
        Employee employee = EmployeeMaker.makeEmployee();
        employee.setId(0);
        Employee expected = EmployeeMaker.makeEmployee();

        Mockito.when(companyService.get(CompanyMaker.COMPANY_ID)).thenReturn(company);
        Mockito.when(employeeRepository.save(employee)).thenReturn(expected);

        Employee result = service.create(request);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void createFailTest() throws AppNotFoundException {
        CreateEmployeeRequest request = EmployeeMaker.makeCreateEmployeeRequest();

        Mockito.when(companyService.get(CompanyMaker.COMPANY_ID))
            .thenThrow(new AppNotFoundException(CompanyMaker.COMPANY_NOT_FOUND, HttpStatus.BAD_REQUEST));

        AppNotFoundException ex = assertThrows(AppNotFoundException.class, () -> service.create(request));
        assertThat(ex.getMessage()).isEqualTo(CompanyMaker.COMPANY_NOT_FOUND);
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
