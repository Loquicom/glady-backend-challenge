package com.challenge.gladybackend.service;

import com.challenge.gladybackend.config.TimeConfig;
import com.challenge.gladybackend.data.entity.Deposit;
import com.challenge.gladybackend.data.entity.Employee;
import com.challenge.gladybackend.data.mapper.DepositMapper;
import com.challenge.gladybackend.data.mapper.EmployeeMapper;
import com.challenge.gladybackend.data.mapper.strategy.DepositGiftStrategy;
import com.challenge.gladybackend.data.mapper.strategy.DepositMealStrategy;
import com.challenge.gladybackend.data.mapper.strategy.DepositTypeStrategy;
import com.challenge.gladybackend.data.request.CreateDepositRequest;
import com.challenge.gladybackend.data.view.EmployeeBalanceView;
import com.challenge.gladybackend.exception.AppInvalidActionException;
import com.challenge.gladybackend.exception.AppNotFoundException;
import com.challenge.gladybackend.exception.AppValidatorException;
import com.challenge.gladybackend.repository.DepositRepository;
import com.challenge.gladybackend.utils.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DepositService {

    private final DepositRepository repository;

    private final EmployeeService employeeService;

    private final CompanyService companyService;

    @Autowired
    public DepositService(DepositRepository depositRepository, EmployeeService employeeService, CompanyService companyService) {
        this.repository = depositRepository;
        this.employeeService = employeeService;
        this.companyService = companyService;
    }

    /**
     * Get one deposit
     *
     * @param id Deposit ID
     * @return The deposit
     * @throws AppNotFoundException The deposit is not found
     */
    public Deposit get(int id) throws AppNotFoundException {
        // Try to get the deposit
        log.info("Get information for deposit {}", id);
        Optional<Deposit> optionalDeposit = repository.findById(id);
        return optionalDeposit.orElseThrow(() -> new AppNotFoundException("Unable to find deposit with id: " + id, HttpStatus.BAD_REQUEST));
    }

    /**
     * Create new deposit
     *
     * @param request Request with data to create the new deposit
     * @return The new deposit
     * @throws AppNotFoundException      Employee associated with the deposit is not found
     * @throws AppInvalidActionException The company don't have enough money to make the deposit
     * @throws AppValidatorException     The deposit type is invalid
     */
    public Deposit create(CreateDepositRequest request) throws AppNotFoundException, AppInvalidActionException, AppValidatorException {
        // Get the employee
        Employee employee = employeeService.get(request.getEmployeeId());
        // Debit the employee's company
        companyService.debit(employee.getCompany(), request.getAmount());
        // Get the date
        Date now = TimeConfig.getTime();
        // Get the strategy for the type
        DepositTypeStrategy strategy = switch (request.getType()) {
            case AppConstants.DEPOSIT_GIFT_TYPE -> new DepositGiftStrategy();
            case AppConstants.DEPOSIT_MEAL_TYPE -> new DepositMealStrategy();
            default -> throw new AppValidatorException("Invalid deposit type", HttpStatus.BAD_REQUEST);
        };
        // Create, save and return
        Deposit deposit = DepositMapper.make(request, now, employee, strategy);
        log.info("Save new deposit: {}", deposit);
        return repository.save(deposit);
    }

    /**
     * Get all deposit for one employee
     *
     * @param employee The employee who own the deposit
     * @return List of deposit
     */
    public List<Deposit> getByEmployee(Employee employee) {
        return getByEmployee(employee.getId());
    }

    /**
     * Get all deposit for one employee
     *
     * @param employeeId The employee's id who own the deposit
     * @return List of deposit
     */
    public List<Deposit> getByEmployee(int employeeId) {
        // List all deposit for one employee
        return repository.findByEmployee_Id(employeeId);
    }

    /**
     * Get all not expired deposit for one employee
     *
     * @param employee The employee who own the deposit
     * @return List of deposit not expired
     */
    public List<Deposit> getByEmployeeNotExpired(Employee employee) {
        return getByEmployeeNotExpired(employee.getId());
    }

    /**
     * Get all not expired deposit for one employee
     *
     * @param employeeId The employee's id who own the deposit
     * @return List of deposit not expired
     */
    public List<Deposit> getByEmployeeNotExpired(int employeeId) {
        // List all deposit for one employee
        return repository.findByExpireAfterAndEmployee_Id(TimeConfig.getTime(), employeeId);
    }

    /**
     * Get the balance (sum of all deposit not expired) for an employee
     *
     * @param employeeId The employee ID
     * @return The view with employee and balance information
     * @throws AppNotFoundException Employee not found
     */
    public EmployeeBalanceView getEmployeeBalance(int employeeId) throws AppNotFoundException {
        // Get the employee and calculate balance
        Employee employee = employeeService.get(employeeId);
        return getEmployeeBalance(employee);
    }

    /**
     * Get the balance (sum of all deposit not expired) for an employee
     *
     * @param employee The employee
     * @return The view with employee and balance information
     */
    public EmployeeBalanceView getEmployeeBalance(Employee employee) {
        // List all deposits not expired
        List<Deposit> deposits = getByEmployeeNotExpired(employee);
        // Sum and make the view
        int sum = deposits.stream().mapToInt(Deposit::getAmount).sum();
        log.info("Employee ({}) {} {} have {} deposits not expired for a total of {}", employee.getId(), employee.getFirstname(),
            employee.getLastname(), deposits.size(), sum);
        return EmployeeMapper.makeEmployeeBalanceView(employee, sum);
    }

}
