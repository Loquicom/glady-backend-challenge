package com.challenge.gladybackend.repository;

import com.challenge.gladybackend.data.entity.Deposit;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface DepositRepository extends CrudRepository<Deposit, Integer> {

    public List<Deposit> findByEmployee_Id(int employee_id);

    public List<Deposit> findByTypeAndEmployee_Id(String type, int employee_id);

    public List<Deposit> findByExpireAfterAndEmployee_Id(Date expire, int employee_id);

    public List<Deposit> findByTypeAndExpireAfterAndEmployee_Id(String type, Date expire, int employee_id);

}
