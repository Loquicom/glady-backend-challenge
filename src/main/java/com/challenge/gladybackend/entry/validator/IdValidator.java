package com.challenge.gladybackend.entry.validator;

import org.springframework.stereotype.Component;

@Component
public class IdValidator extends Validator<Integer> {

    public static final String ID_GREATER_THAN_ZERO = "An Id is always greater than 0";

    @Override
    protected void validate(Integer data) {
        assertThat(data > 0).isTrue().orError(ID_GREATER_THAN_ZERO);
    }
}
