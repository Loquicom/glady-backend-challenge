package com.challenge.gladybackend.entry.validator;

import com.challenge.gladybackend.utils.AppConstants;
import org.springframework.stereotype.Component;

@Component
public class TypeValidator extends Validator<String> {

    public static final String UNKNOWN_TYPE = "Unknown type";

    @Override
    protected void validate(String data) {
        assertThat(AppConstants.TYPES.contains(data)).isTrue().orError(UNKNOWN_TYPE);
    }

}
