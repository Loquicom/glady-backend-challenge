package com.challenge.gladybackend.entry.controller;

import com.challenge.gladybackend.data.dto.EmployeeDTO;
import com.challenge.gladybackend.data.entity.Employee;
import com.challenge.gladybackend.data.mapper.EmployeeMapper;
import com.challenge.gladybackend.data.request.CreateEmployeeRequest;
import com.challenge.gladybackend.data.view.EmployeeBalanceView;
import com.challenge.gladybackend.entry.validator.CreateEmployeeValidator;
import com.challenge.gladybackend.entry.validator.IdValidator;
import com.challenge.gladybackend.exception.AppNotFoundException;
import com.challenge.gladybackend.exception.AppValidatorException;
import com.challenge.gladybackend.service.DepositService;
import com.challenge.gladybackend.service.EmployeeService;
import com.challenge.gladybackend.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("${app.api.root-path}/employees")
public class EmployeeController {

    private final EmployeeService service;

    private final DepositService depositService;

    private final CreateEmployeeValidator createEmployeeValidator;

    private final IdValidator idValidator;

    @Autowired
    public EmployeeController(EmployeeService employeeService, DepositService depositService,
        CreateEmployeeValidator createEmployeeValidator, IdValidator idValidator) {
        Assert.notNull(employeeService, "EmployeeService must not be null!");
        Assert.notNull(depositService, "DepositService must not be null!");
        Assert.notNull(createEmployeeValidator, "CreateEmployeeValidator must not be null!");
        Assert.notNull(idValidator, "IdValidator must not be null!");
        this.service = employeeService;
        this.depositService = depositService;
        this.createEmployeeValidator = createEmployeeValidator;
        this.idValidator = idValidator;
    }

    /**
     * Get one Employee
     *
     * @param id Employee ID
     * @return The employee
     * @throws AppNotFoundException  Employee not found
     * @throws AppValidatorException ID invalid
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> get(@PathVariable int id) throws AppNotFoundException, AppValidatorException {
        log.info("Call get employee: id={}", id);
        // Check ID
        idValidator.isValidOrThrow(id);
        // Get employee, transform to DTO and return
        Employee employee = service.get(id);
        return ResponseUtils.response(EmployeeMapper.toDTO(employee));
    }

    /**
     * Create a new employee
     *
     * @param request Data to create the new employee
     * @return The new employee
     * @throws AppValidatorException Request invalid
     * @throws AppNotFoundException  Employee's company not found
     */
    @PostMapping
    public ResponseEntity<EmployeeDTO> create(@RequestBody CreateEmployeeRequest request)
        throws AppValidatorException, AppNotFoundException {
        log.info("Call create employee: request={}", request);
        // Check request
        createEmployeeValidator.isValidOrThrow(request);
        // Create new employee, transform to DTO and return
        Employee employee = service.create(request);
        return ResponseUtils.response(EmployeeMapper.toDTO(employee));
    }

    /**
     * Get the employee balance
     *
     * @param id Employee ID
     * @return Employee with his balance
     * @throws AppValidatorException id is invalid
     * @throws AppNotFoundException  Employee not found
     */
    @GetMapping("/{id}/balance")
    public ResponseEntity<EmployeeBalanceView> getBalance(@PathVariable int id) throws AppValidatorException, AppNotFoundException {
        log.info("Call get balance: id={}", id);
        // Check id
        idValidator.isValidOrThrow(id);
        // Get Emplayee balance view and return
        EmployeeBalanceView view = depositService.getEmployeeBalance(id);
        return ResponseUtils.response(view);
    }

}
