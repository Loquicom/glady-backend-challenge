package com.challenge.gladybackend.data.mapper;

import com.challenge.gladybackend.data.dto.EmployeeDTO;
import com.challenge.gladybackend.data.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDTO toDTO(Employee entity) {
        return new EmployeeDTO(entity.getId(), entity.getFirstname(), entity.getLastname(), entity.getCompany().getId());
    }

    public static Employee toEntity(EmployeeDTO dto) {
        Employee entity = new Employee();
        entity.setId(dto.getId());
        entity.setFirstname(dto.getFirstname());
        entity.setLastname(dto.getLastname());
        return entity;
    }

}
