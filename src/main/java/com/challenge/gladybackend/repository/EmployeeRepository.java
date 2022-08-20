package com.challenge.gladybackend.repository;

import com.challenge.gladybackend.data.entity.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
}
