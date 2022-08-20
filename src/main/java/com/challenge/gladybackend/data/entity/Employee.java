package com.challenge.gladybackend.data.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "em_id")
    private int id;

    private String firstname;

    private String lastname;

}
