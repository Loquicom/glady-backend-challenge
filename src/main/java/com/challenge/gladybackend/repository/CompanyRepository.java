package com.challenge.gladybackend.repository;

import com.challenge.gladybackend.data.entity.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Integer> {
}
