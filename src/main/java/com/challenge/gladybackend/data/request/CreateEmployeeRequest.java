package com.challenge.gladybackend.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateEmployeeRequest {

    private String firstname;

    private String lastname;

    @JsonProperty("company_id")
    private int companyId;

}
