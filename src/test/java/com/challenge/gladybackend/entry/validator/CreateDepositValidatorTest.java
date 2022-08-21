package com.challenge.gladybackend.entry.validator;

import com.challenge.gladybackend.data.request.CreateDepositRequest;
import com.challenge.gladybackend.helper.DepositMaker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class CreateDepositValidatorTest {

    @Autowired
    private CreateDepositValidator validator;

    @Test
    public void contextLoad() {
        assertThat(validator).isNotNull();
    }

    @Test
    public void validTest() {
        CreateDepositRequest request = DepositMaker.makeCreateDepositRequest();
        assertThat(validator.isValid(request)).isTrue();
    }

    @Test
    public void notValidTest() {
        CreateDepositRequest request = new CreateDepositRequest();
        assertThat(validator.isValid(request)).isFalse();
        assertThat(validator.getNumberOfErrors()).isEqualTo(2);
        assertThat(validator.getErrors().contains(CreateDepositValidator.DEPOSIT_ID_GREATER_THAN_ZERO)).isTrue();
        assertThat(validator.getErrors().contains(CreateDepositValidator.DEPOSIT_UNKNOWN_TYPE)).isTrue();

        request.setType("      ");
        assertThat(validator.isValid(request)).isFalse();
        assertThat(validator.getNumberOfErrors()).isEqualTo(2);
        assertThat(validator.getErrors().contains(CreateDepositValidator.DEPOSIT_ID_GREATER_THAN_ZERO)).isTrue();
        assertThat(validator.getErrors().contains(CreateDepositValidator.DEPOSIT_UNKNOWN_TYPE)).isTrue();

        request.setType("Not a type");
        assertThat(validator.isValid(request)).isFalse();
        assertThat(validator.getNumberOfErrors()).isEqualTo(2);
        assertThat(validator.getErrors().contains(CreateDepositValidator.DEPOSIT_ID_GREATER_THAN_ZERO)).isTrue();
        assertThat(validator.getErrors().contains(CreateDepositValidator.DEPOSIT_UNKNOWN_TYPE)).isTrue();

        request.setEmployeeId(-8);
        assertThat(validator.isValid(request)).isFalse();
        assertThat(validator.getNumberOfErrors()).isEqualTo(2);
        assertThat(validator.getErrors().contains(CreateDepositValidator.DEPOSIT_ID_GREATER_THAN_ZERO)).isTrue();
        assertThat(validator.getErrors().contains(CreateDepositValidator.DEPOSIT_UNKNOWN_TYPE)).isTrue();

        request.setAmount(-8);
        assertThat(validator.isValid(request)).isFalse();
        assertThat(validator.getNumberOfErrors()).isEqualTo(3);
        assertThat(validator.getErrors().contains(CreateDepositValidator.DEPOSIT_ID_GREATER_THAN_ZERO)).isTrue();
        assertThat(validator.getErrors().contains(CreateDepositValidator.DEPOSIT_UNKNOWN_TYPE)).isTrue();
        assertThat(validator.getErrors().contains(CreateDepositValidator.AMOUNT_POSITIVE)).isTrue();
    }

}
