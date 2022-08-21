package com.challenge.gladybackend.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateDepositRequest {

    @JsonProperty("employee_id")
    private int employeeId;

    private int amount;

    private String type;

}
