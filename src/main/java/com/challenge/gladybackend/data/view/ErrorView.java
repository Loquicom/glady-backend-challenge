package com.challenge.gladybackend.data.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorView {

    private int code;

    private String message;

    private List<String> trace;

}
