package com.challenge.gladybackend.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private int id;

    private String firstname;

    private String lastname;

    @JsonProperty("company_id")
    private int companyId;

}
