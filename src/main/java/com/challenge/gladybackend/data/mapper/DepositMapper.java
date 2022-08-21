package com.challenge.gladybackend.data.mapper;

import com.challenge.gladybackend.data.dto.DepositDTO;
import com.challenge.gladybackend.data.entity.Deposit;
import com.challenge.gladybackend.data.entity.Employee;
import com.challenge.gladybackend.data.mapper.strategy.DepositTypeStrategy;
import com.challenge.gladybackend.data.request.CreateDepositRequest;

import java.util.Date;

public class DepositMapper {

    public static DepositDTO toDTO(Deposit entity) {
        return new DepositDTO(entity.getId(), entity.getAmount(), entity.getDate(), entity.getExpire(), entity.getType(),
            entity.getEmployee().getId());
    }

    public static Deposit toEntity(DepositDTO dto) {
        Deposit entity = new Deposit();
        entity.setId(dto.getId());
        entity.setAmount(dto.getAmount());
        entity.setDate(dto.getDate());
        entity.setExpire(dto.getExpire());
        entity.setType(dto.getType());
        return entity;
    }

    public static Deposit make(CreateDepositRequest data, Date now, Employee employee, DepositTypeStrategy strategy) {
        Deposit deposit = new Deposit();
        deposit.setAmount(data.getAmount());
        deposit.setDate(now);
        deposit.setExpire(strategy.calculateExpireDate(now));
        deposit.setType(data.getType());
        deposit.setEmployee(employee);
        return deposit;
    }

}
