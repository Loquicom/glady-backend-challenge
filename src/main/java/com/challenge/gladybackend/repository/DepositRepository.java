package com.challenge.gladybackend.repository;

import com.challenge.gladybackend.data.entity.Deposit;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface DepositRepository extends CrudRepository<Deposit, Integer> {

    public List<Deposit> findByType(String type);

    public List<Deposit> findByExpireAfter(Date date);

    public List<Deposit> findByTypeAndExpireAfter(String type, Date date);

}
