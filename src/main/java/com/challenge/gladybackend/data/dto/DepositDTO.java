package com.challenge.gladybackend.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositDTO {

    private int id;

    private int amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date date;

    /**
     * Expire date
     * The first day when the deposit is not available
     * For exemple if a deposit is available from 2020-01-01 to 2021-02-28 expire date is 2021-03-01
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date expire;

    private String type;

    @JsonProperty("employee_id")
    private int employeeId;

}
