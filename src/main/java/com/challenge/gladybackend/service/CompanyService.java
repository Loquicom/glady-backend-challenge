package com.challenge.gladybackend.service;

import com.challenge.gladybackend.data.entity.Company;
import com.challenge.gladybackend.data.request.CreateCompanyRequest;
import com.challenge.gladybackend.data.request.CreditCompanyRequest;
import com.challenge.gladybackend.exception.AppNotFoundException;
import com.challenge.gladybackend.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Slf4j
@Service
public class CompanyService {

    private final CompanyRepository repository;

    @Autowired
    public CompanyService(CompanyRepository repository) {
        Assert.notNull(repository, "CompanyRepository must not be null!");
        this.repository = repository;
    }

    /**
     * Get one company
     *
     * @param id Company ID
     * @return Company
     * @throws AppNotFoundException When company is not found
     */
    public Company get(int id) throws AppNotFoundException {
        // Try to get the company
        log.info("Get information for company {}", id);
        Optional<Company> optionalCompany = repository.findById(id);
        return optionalCompany.orElseThrow(() -> new AppNotFoundException("Unable to find company with id: " + id, HttpStatus.BAD_REQUEST));
    }

    /**
     * Create a new company
     *
     * @param request Request with company's information
     * @return New company
     */
    public Company create(CreateCompanyRequest request) {
        // Create new company object
        Company company = new Company();
        company.setName(request.getName());
        company.setBalance(request.getBalance());
        // Save in db and return
        log.info("Save new company: {}", company);
        return repository.save(company);
    }

    /**
     * Credit one amount in the balance of one company
     *
     * @param id      Company ID
     * @param request Credit request
     * @return Company with the new balance
     * @throws AppNotFoundException When company is not found
     */
    public Company credit(int id, CreditCompanyRequest request) throws AppNotFoundException {
        // Get company
        Company company = get(id);
        // Add amount in balance
        int balance = company.getBalance() + request.getAmount();
        log.info("New balance set to {} (+{}) for the company ({}) {}", balance, request.getAmount(), company.getId(), company.getName());
        company.setBalance(balance);
        // Save and return
        return repository.save(company);
    }

}
