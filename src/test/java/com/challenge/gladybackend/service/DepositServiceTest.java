package com.challenge.gladybackend.service;

import com.challenge.gladybackend.config.TimeConfig;
import com.challenge.gladybackend.data.entity.Deposit;
import com.challenge.gladybackend.data.entity.Employee;
import com.challenge.gladybackend.data.request.CreateDepositRequest;
import com.challenge.gladybackend.exception.AppInvalidActionException;
import com.challenge.gladybackend.exception.AppNotFoundException;
import com.challenge.gladybackend.exception.AppValidatorException;
import com.challenge.gladybackend.helper.DepositMaker;
import com.challenge.gladybackend.helper.EmployeeMaker;
import com.challenge.gladybackend.repository.DepositRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class DepositServiceTest {

    @Autowired
    private DepositService service;

    @MockBean
    private DepositRepository depositRepository;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private CompanyService companyService;

    @Test
    public void contextLoad() {
        assertThat(service).isNotNull();
    }

    @Test
    public void getSuccessTest() throws AppNotFoundException {
        Deposit deposit = DepositMaker.makeDeposit();

        Mockito.when(depositRepository.findById(DepositMaker.DEPOSIT_ID)).thenReturn(Optional.of(deposit));

        Deposit result = service.get(DepositMaker.DEPOSIT_ID);
        assertThat(result).isEqualTo(deposit);
    }

    @Test
    public void getFailTest() {
        Mockito.when(depositRepository.findById(DepositMaker.DEPOSIT_ID)).thenReturn(Optional.empty());

        AppNotFoundException ex = assertThrows(AppNotFoundException.class, () -> service.get(DepositMaker.DEPOSIT_ID));
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createSuccessTest() throws AppNotFoundException, AppValidatorException, AppInvalidActionException {
        //Set correct time for the test
        TimeConfig.setTime(DepositMaker.DEPOSIT_DATE);
        // Create objects & test
        CreateDepositRequest request = DepositMaker.makeCreateDepositRequest();
        Employee employee = EmployeeMaker.makeEmployee();
        Deposit deposit = DepositMaker.makeDeposit();
        deposit.setId(0);
        Deposit expected = DepositMaker.makeDeposit();

        Mockito.when(employeeService.get(EmployeeMaker.EMPLOYEE_ID)).thenReturn(employee);
        Mockito.when(depositRepository.save(deposit)).thenReturn(expected);

        Deposit result = service.create(request);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void createEmployeeNotFoundTest() throws AppNotFoundException {
        CreateDepositRequest request = DepositMaker.makeCreateDepositRequest();

        Mockito.when(employeeService.get(EmployeeMaker.EMPLOYEE_ID))
            .thenThrow(new AppNotFoundException(EmployeeMaker.EMPLOYEE_NOT_FOUND, HttpStatus.BAD_REQUEST));

        AppNotFoundException ex = assertThrows(AppNotFoundException.class, () -> service.create(request));
        assertThat(ex.getMessage()).isEqualTo(EmployeeMaker.EMPLOYEE_NOT_FOUND);
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createInvalidTypeTest() throws AppNotFoundException {
        CreateDepositRequest request = DepositMaker.makeCreateDepositRequest();
        request.setType("Test");
        Employee employee = EmployeeMaker.makeEmployee();

        Mockito.when(employeeService.get(EmployeeMaker.EMPLOYEE_ID)).thenReturn(employee);

        AppValidatorException ex = assertThrows(AppValidatorException.class, () -> service.create(request));
        assertThat(ex.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void getByEmployeeTest() {
        Employee employee = EmployeeMaker.makeEmployee();
        Deposit deposit1 = DepositMaker.makeDeposit();
        Deposit deposit2 = DepositMaker.makeDeposit();
        deposit2.setId(2);
        List<Deposit> deposits = Arrays.asList(deposit1, deposit2);

        Mockito.when(depositRepository.findByEmployee_Id(EmployeeMaker.EMPLOYEE_ID)).thenReturn(deposits);

        assertThat(service.getByEmployee(employee)).isEqualTo(deposits);
        assertThat(service.getByEmployee(EmployeeMaker.EMPLOYEE_ID)).isEqualTo(deposits);
    }

    @Test
    public void getByEmployeeNotExpiredTest() {
        //Set correct time for the test
        TimeConfig.setTime(DepositMaker.DEPOSIT_DATE);
        // Create objects & test
        Employee employee = EmployeeMaker.makeEmployee();
        Deposit deposit1 = DepositMaker.makeDeposit();
        Deposit deposit2 = DepositMaker.makeDeposit();
        deposit2.setId(2);
        List<Deposit> deposits = Arrays.asList(deposit1, deposit2);

        Mockito.when(depositRepository.findByExpireAfterAndEmployee_Id(TimeConfig.getTime(), EmployeeMaker.EMPLOYEE_ID))
            .thenReturn(deposits);

        assertThat(service.getByEmployeeNotExpired(employee)).isEqualTo(deposits);
        assertThat(service.getByEmployeeNotExpired(EmployeeMaker.EMPLOYEE_ID)).isEqualTo(deposits);
    }

}
