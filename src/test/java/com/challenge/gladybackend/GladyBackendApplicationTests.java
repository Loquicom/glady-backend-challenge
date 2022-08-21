package com.challenge.gladybackend;

import com.challenge.gladybackend.config.TimeConfig;
import com.challenge.gladybackend.data.entity.Company;
import com.challenge.gladybackend.data.entity.Deposit;
import com.challenge.gladybackend.data.entity.Employee;
import com.challenge.gladybackend.data.request.CreateCompanyRequest;
import com.challenge.gladybackend.data.request.CreateDepositRequest;
import com.challenge.gladybackend.data.request.CreateEmployeeRequest;
import com.challenge.gladybackend.data.view.EmployeeBalanceView;
import com.challenge.gladybackend.exception.AppInvalidActionException;
import com.challenge.gladybackend.exception.AppNotFoundException;
import com.challenge.gladybackend.exception.AppValidatorException;
import com.challenge.gladybackend.service.CompanyService;
import com.challenge.gladybackend.service.DepositService;
import com.challenge.gladybackend.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class GladyBackendApplicationTests {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepositService depositService;

    @Test
    public void contextLoad() {
        assertThat(companyService).isNotNull();
        assertThat(employeeService).isNotNull();
        assertThat(depositService).isNotNull();
    }

    @Test
    public void appTest() throws AppNotFoundException, AppValidatorException, AppInvalidActionException {
        int baseCompanyBalance = 100;
        // Set date to 2154-04-11
        log.info("Time set to 2154-04-11");
        TimeConfig.setTime("2154", "04", "11");
        // Create company
        log.info("Create company called Systems Alliance with 100€");
        CreateCompanyRequest createCompanyRequest = new CreateCompanyRequest();
        createCompanyRequest.setName("Systems Alliance");
        createCompanyRequest.setBalance(baseCompanyBalance);
        Company company = companyService.create(createCompanyRequest);
        // Add employee
        log.info("Add employee in Systems Alliance called Jane Shepard");
        CreateEmployeeRequest createEmployeeRequest = new CreateEmployeeRequest();
        createEmployeeRequest.setCompanyId(company.getId());
        createEmployeeRequest.setFirstname("Jane");
        createEmployeeRequest.setLastname("Shepard");
        Employee employee = employeeService.create(createEmployeeRequest);
        // Give one gift deposit to Jane
        log.info("Give one gift deposit to Jane worth 60€");
        CreateDepositRequest giftDepositRequest = new CreateDepositRequest();
        giftDepositRequest.setAmount(60);
        giftDepositRequest.setType("GIFT");
        giftDepositRequest.setEmployeeId(employee.getId());
        Deposit giftDeposit = depositService.create(giftDepositRequest);
        // Check the company balance
        log.info("Verify the new company balance");
        company = companyService.get(company.getId());
        assertThat(company.getBalance()).isEqualTo(baseCompanyBalance - giftDeposit.getAmount());
        log.info("Success");
        // Give one meal deposit to Jane
        log.info("Give one meal deposit to Jane worth 40€");
        CreateDepositRequest mealDepositRequest = new CreateDepositRequest();
        mealDepositRequest.setAmount(40);
        mealDepositRequest.setType("MEAL");
        mealDepositRequest.setEmployeeId(employee.getId());
        Deposit mealDeposit = depositService.create(mealDepositRequest);
        // Check the company balance
        log.info("Verify the new company balance");
        company = companyService.get(company.getId());
        assertThat(company.getBalance()).isEqualTo(baseCompanyBalance - giftDeposit.getAmount() - mealDeposit.getAmount());
        log.info("Success");
        // Try to give another gift deposit
        log.info("Try to give one gift deposit to Jane worth 50€, error expected the company balance don't have enough money");
        CreateDepositRequest createDepositRequest = new CreateDepositRequest();
        createDepositRequest.setAmount(50);
        createDepositRequest.setType("GIFT");
        createDepositRequest.setEmployeeId(employee.getId());
        assertThrows(AppInvalidActionException.class, () -> depositService.create(createDepositRequest));
        log.info("Success");
        // Get Jane's balance
        log.info("Get Jane's balance");
        EmployeeBalanceView employeeBalanceView = depositService.getEmployeeBalance(employee);
        log.info("Employee with balance information: {}", employeeBalanceView);
        assertThat(employeeBalanceView.getBalance()).isEqualTo(giftDeposit.getAmount() + mealDeposit.getAmount());
        log.info("Success");
        // Set date to 2155-02-21
        log.info("Time set to 2155-02-21");
        TimeConfig.setTime("2155", "02", "21");
        // All deposits are still available
        log.info("Test if all deposits are still available");
        employeeBalanceView = depositService.getEmployeeBalance(employee);
        assertThat(employeeBalanceView.getBalance()).isEqualTo(giftDeposit.getAmount() + mealDeposit.getAmount());
        log.info("Success");
        // Set date to 2155-03-01
        log.info("Time set to 2155-03-01");
        TimeConfig.setTime("2155", "03", "01");
        // Only Gift deposit is still available
        log.info("Test if only gift deposit is still available");
        employeeBalanceView = depositService.getEmployeeBalance(employee);
        assertThat(employeeBalanceView.getBalance()).isEqualTo(giftDeposit.getAmount());
        log.info("Success");
        // Set date to 2155-04-11
        log.info("Time set to 2155-04-11");
        TimeConfig.setTime("2155", "04", "11");
        // No deposit available
        log.info("Test if no deposit is still available");
        employeeBalanceView = depositService.getEmployeeBalance(employee);
        assertThat(employeeBalanceView.getBalance()).isEqualTo(0);
        log.info("Success");
    }

}
