package com.challenge.gladybackend.entry.validator;

import com.challenge.gladybackend.data.request.CreditCompanyRequest;
import org.springframework.stereotype.Component;

@Component
public class CreditCompanyValidator extends Validator<CreditCompanyRequest> {

    public static final String AMOUNT_POSITIVE = "Amount can't be negative";

    @Override
    protected void validate(CreditCompanyRequest data) {
        assertThat(data.getAmount() >= 0).isTrue().orError(AMOUNT_POSITIVE);
    }
}
