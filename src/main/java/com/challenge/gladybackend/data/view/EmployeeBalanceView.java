package com.challenge.gladybackend.data.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeBalanceView {

    private int id;

    private String firstname;

    private String lastname;

    private int balance;

    @JsonProperty("company_id")
    private int companyId;

}
