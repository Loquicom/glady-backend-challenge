package com.challenge.gladybackend.entry.validator;

import com.challenge.gladybackend.data.request.CreateEmployeeRequest;
import org.springframework.stereotype.Component;

@Component
public class CreateEmployeeValidator extends Validator<CreateEmployeeRequest> {

    public static final String FIRSTNAME_REQUIRED = "Firstname is required";
    public static final String LASTNAME_REQUIRED = "Lastname is required";
    public static final String COMPANY_ID_GREATER_THAN_ZERO = "Company Id is always greater than 0";

    @Override
    protected void validate(CreateEmployeeRequest data) {
        assertThat(data.getFirstname()).isNotBlank().orError(FIRSTNAME_REQUIRED);
        assertThat(data.getLastname()).isNotBlank().orError(LASTNAME_REQUIRED);
        assertThat(data.getCompanyId() > 0).isTrue().orError(COMPANY_ID_GREATER_THAN_ZERO);
    }

}
