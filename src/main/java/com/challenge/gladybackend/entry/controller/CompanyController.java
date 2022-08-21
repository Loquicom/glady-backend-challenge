package com.challenge.gladybackend.entry.controller;

import com.challenge.gladybackend.data.entity.Company;
import com.challenge.gladybackend.data.request.CreateCompanyRequest;
import com.challenge.gladybackend.data.request.CreditCompanyRequest;
import com.challenge.gladybackend.entry.validator.CreateCompanyValidator;
import com.challenge.gladybackend.entry.validator.CreditCompanyValidator;
import com.challenge.gladybackend.entry.validator.IdValidator;
import com.challenge.gladybackend.exception.AppNotFoundException;
import com.challenge.gladybackend.exception.AppValidatorException;
import com.challenge.gladybackend.service.CompanyService;
import com.challenge.gladybackend.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("${app.api.root-path}/companies")
public class CompanyController {

    private final CreateCompanyValidator createCompanyValidator;

    private final IdValidator idValidator;

    private final CreditCompanyValidator creditCompanyValidator;

    private final CompanyService service;

    @Autowired
    public CompanyController(CreateCompanyValidator createCompanyValidator, IdValidator idValidator,
        CreditCompanyValidator creditCompanyValidator, CompanyService companyService) {
        Assert.notNull(createCompanyValidator, "CreateCompanyValidator must not be null!");
        Assert.notNull(idValidator, "IdValidator must not be null!");
        Assert.notNull(creditCompanyValidator, "CreditCompanyValidator must not be null!");
        Assert.notNull(companyService, "CompanyService must not be null!");
        this.createCompanyValidator = createCompanyValidator;
        this.idValidator = idValidator;
        this.creditCompanyValidator = creditCompanyValidator;
        this.service = companyService;
    }

    /**
     * Get one company
     *
     * @param id Company ID
     * @return Company
     * @throws AppNotFoundException When company is not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Company> get(@PathVariable int id) throws AppNotFoundException, AppValidatorException {
        log.info("Call get company: id={}", id);
        idValidator.isValidOrThrow(id);
        Company company = service.get(id);
        return ResponseUtils.response(company);
    }

    /**
     * Create a new company
     *
     * @param request Request with company's information
     * @return New company
     * @throws AppValidatorException Request is not valid
     */
    @PostMapping
    public ResponseEntity<Company> create(@RequestBody CreateCompanyRequest request) throws AppValidatorException {
        log.info("Call create company: request={}", request);
        // Valid request
        createCompanyValidator.isValidOrThrow(request);
        // Create new company
        Company company = service.create(request);
        return ResponseUtils.response(company);
    }

    /**
     * Credit one amount in the balance of one company
     *
     * @param id      Company ID
     * @param request Credit request
     * @return Company with the new balance
     * @throws AppNotFoundException  When company is not found
     * @throws AppValidatorException When amount is negative
     */
    @PutMapping("/{id}/credit")
    public ResponseEntity<Company> credit(@PathVariable int id, @RequestBody CreditCompanyRequest request)
        throws AppValidatorException, AppNotFoundException {
        log.info("Call credit company: id={},request={}", id, request);
        // Check parameters
        idValidator.isValidOrThrow(id);
        creditCompanyValidator.isValidOrThrow(request);
        // Credit
        Company company = service.credit(id, request);
        return ResponseUtils.response(company);
    }

}
