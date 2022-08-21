package com.challenge.gladybackend.entry.validator;

import com.challenge.gladybackend.data.request.CreateCompanyRequest;
import org.springframework.stereotype.Component;

@Component
public class CreateCompanyValidator extends Validator<CreateCompanyRequest> {

    public static final String NAME_REQUIRED = "Name is required";
    public static final String BALANCE_POSITIVE = "Balance can't be negative";

    @Override
    protected void validate(CreateCompanyRequest data) {
        assertThat(data.getName()).isNotBlank().orError(NAME_REQUIRED);
        assertThat(data.getBalance() >= 0).isTrue().orError(BALANCE_POSITIVE);
    }

}
