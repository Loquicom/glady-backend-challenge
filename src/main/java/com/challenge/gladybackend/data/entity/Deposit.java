package com.challenge.gladybackend.data.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "deposit")
public class Deposit {

    @Id
    @Column(name = "de_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int amount;

    private Date date;

    /**
     * Expire date
     * The first day when the deposit is not available
     * For exemple if a deposit is available from 2020-01-01 to 2021-02-28 expire date is 2021-03-01
     */
    private Date expire;

    private String type;

    @ManyToOne
    @JoinColumn(name = "em_id")
    private Employee employee;

}
