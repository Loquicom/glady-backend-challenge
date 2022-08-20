package com.challenge.gladybackend.data.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "company")
public class Company {

    @Id
    @Column(name = "co_id")
    private int id;

    private String name;

    private int balance;

}
