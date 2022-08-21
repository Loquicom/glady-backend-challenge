package com.challenge.gladybackend.entry.validator;

import com.challenge.gladybackend.data.request.CreateCompanyRequest;
import com.challenge.gladybackend.helper.CompanyMaker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class CreateCompanyValidatorTest {

    @Autowired
    private CreateCompanyValidator validator;

    @Test
    public void contextLoad() {
        assertThat(validator).isNotNull();
    }

    @Test
    public void validTest() {
        CreateCompanyRequest request = CompanyMaker.makeCreateCompanyRequest();
        assertThat(validator.isValid(request)).isTrue();
    }

    @Test
    public void notValidTest() {
        CreateCompanyRequest request = new CreateCompanyRequest();
        assertThat(validator.isValid(request)).isFalse();
        assertThat(validator.getNumberOfErrors()).isEqualTo(1);
        assertThat(validator.getErrors().contains(CreateCompanyValidator.NAME_REQUIRED)).isTrue();

        request.setName("");
        assertThat(validator.isValid(request)).isFalse();
        assertThat(validator.getNumberOfErrors()).isEqualTo(1);
        assertThat(validator.getErrors().contains(CreateCompanyValidator.NAME_REQUIRED)).isTrue();

        request.setName("               ");
        assertThat(validator.isValid(request)).isFalse();
        assertThat(validator.getNumberOfErrors()).isEqualTo(1);
        assertThat(validator.getErrors().contains(CreateCompanyValidator.NAME_REQUIRED)).isTrue();

        request.setBalance(-2);
        assertThat(validator.isValid(request)).isFalse();
        assertThat(validator.getNumberOfErrors()).isEqualTo(2);
        assertThat(validator.getErrors().contains(CreateCompanyValidator.NAME_REQUIRED)).isTrue();
        assertThat(validator.getErrors().contains(CreateCompanyValidator.BALANCE_POSITIVE)).isTrue();

        request.setName("Test");
        assertThat(validator.isValid(request)).isFalse();
        assertThat(validator.getNumberOfErrors()).isEqualTo(1);
        assertThat(validator.getErrors().contains(CreateCompanyValidator.BALANCE_POSITIVE)).isTrue();
    }

}
