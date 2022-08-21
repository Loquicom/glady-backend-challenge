package com.challenge.gladybackend.entry.validator;

import com.challenge.gladybackend.data.request.CreditCompanyRequest;
import com.challenge.gladybackend.helper.CompanyMaker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class CreditCompanyValidatorTest {

    @Autowired
    private CreditCompanyValidator validator;

    @Test
    public void contextLoad() {
        assertThat(validator).isNotNull();
    }

    @Test
    public void validTest() {
        CreditCompanyRequest request = CompanyMaker.makeCreditCompanyRequest();
        assertThat(validator.isValid(request)).isTrue();

        request.setAmount(0);
        assertThat(validator.isValid(request)).isTrue();
    }

    @Test
    public void notValidTest() {
        CreditCompanyRequest request = new CreditCompanyRequest();
        request.setAmount(-8);
        assertThat(validator.isValid(request)).isFalse();
        assertThat(validator.getNumberOfErrors()).isEqualTo(1);
        assertThat(validator.getErrors().contains(CreditCompanyValidator.AMOUNT_POSITIVE)).isTrue();
    }

}
