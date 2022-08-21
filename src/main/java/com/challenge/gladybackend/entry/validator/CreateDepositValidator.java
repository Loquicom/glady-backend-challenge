package com.challenge.gladybackend.entry.validator;

import com.challenge.gladybackend.data.request.CreateDepositRequest;
import com.challenge.gladybackend.utils.AppConstants;
import org.springframework.stereotype.Component;

@Component
public class CreateDepositValidator extends Validator<CreateDepositRequest> {

    public static final String DEPOSIT_ID_GREATER_THAN_ZERO = "Deposit Id is always greater than 0";
    public static final String DEPOSIT_UNKNOWN_TYPE = "Unknown type";
    public static final String AMOUNT_POSITIVE = "Amount can't be negative";

    @Override
    protected void validate(CreateDepositRequest data) {
        assertThat(data.getEmployeeId() > 0).isTrue().orError(DEPOSIT_ID_GREATER_THAN_ZERO);
        assertThat(AppConstants.TYPES.contains(data.getType())).isTrue().orError(DEPOSIT_UNKNOWN_TYPE);
        assertThat(data.getAmount() >= 0).isTrue().orError(AMOUNT_POSITIVE);
    }

}
