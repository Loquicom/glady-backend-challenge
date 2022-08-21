package com.challenge.gladybackend.service;

import com.challenge.gladybackend.data.entity.Company;
import com.challenge.gladybackend.data.entity.Employee;
import com.challenge.gladybackend.data.request.CreateEmployeeRequest;
import com.challenge.gladybackend.exception.AppNotFoundException;
import com.challenge.gladybackend.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Slf4j
@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    private final CompanyService companyService;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, CompanyService companyService) {
        Assert.notNull(employeeRepository, "EmployeeRepository must not be null!");
        Assert.notNull(companyService, "CompanyService must not be null!");
        this.repository = employeeRepository;
        this.companyService = companyService;
    }

    /**
     * Get an employee
     *
     * @param id Employee ID
     * @return The employee
     * @throws AppNotFoundException Employee not found
     */
    public Employee get(int id) throws AppNotFoundException {
        // Try to get the employee
        log.info("Get information for employee {}", id);
        Optional<Employee> optionalEmployee = repository.findById(id);
        return optionalEmployee.orElseThrow(
            () -> new AppNotFoundException("Unable to find employee with id: " + id, HttpStatus.BAD_REQUEST));
    }

    /**
     * Create a new employee
     *
     * @param request Request with data to create the new employee
     * @return The new employee
     * @throws AppNotFoundException Employee's company not found
     */
    public Employee create(CreateEmployeeRequest request) throws AppNotFoundException {
        // Get the company
        Company company = companyService.get(request.getCompanyId());
        // Create new employee
        Employee employee = new Employee();
        employee.setFirstname(request.getFirstname());
        employee.setLastname(request.getLastname());
        employee.setCompany(company);
        // Save, Transform and return
        log.info("Save new employee: {}", employee);
        return repository.save(employee);
    }

}
