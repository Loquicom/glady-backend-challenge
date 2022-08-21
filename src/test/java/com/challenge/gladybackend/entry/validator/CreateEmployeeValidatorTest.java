package com.challenge.gladybackend.entry.validator;

import com.challenge.gladybackend.data.request.CreateEmployeeRequest;
import com.challenge.gladybackend.helper.EmployeeMaker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class CreateEmployeeValidatorTest {

    @Autowired
    private CreateEmployeeValidator validator;

    @Test
    public void contextLoad() {
        assertThat(validator).isNotNull();
    }

    @Test
    public void validTest() {
        CreateEmployeeRequest request = EmployeeMaker.makeCreateEmployeeRequest();
        assertThat(validator.isValid(request)).isTrue();
    }

    @Test
    public void notValidTest() {
        CreateEmployeeRequest request = new CreateEmployeeRequest();
        assertThat(validator.isValid(request)).isFalse();
        assertThat(validator.getNumberOfErrors()).isEqualTo(3);
        assertThat(validator.getErrors().contains(CreateEmployeeValidator.FIRSTNAME_REQUIRED)).isTrue();
        assertThat(validator.getErrors().contains(CreateEmployeeValidator.LASTNAME_REQUIRED)).isTrue();
        assertThat(validator.getErrors().contains(CreateEmployeeValidator.COMPANY_ID_GREATER_THAN_ZERO)).isTrue();

        request.setFirstname("");
        request.setLastname("         ");
        assertThat(validator.isValid(request)).isFalse();
        assertThat(validator.getNumberOfErrors()).isEqualTo(3);
        assertThat(validator.getErrors().contains(CreateEmployeeValidator.FIRSTNAME_REQUIRED)).isTrue();
        assertThat(validator.getErrors().contains(CreateEmployeeValidator.LASTNAME_REQUIRED)).isTrue();
        assertThat(validator.getErrors().contains(CreateEmployeeValidator.COMPANY_ID_GREATER_THAN_ZERO)).isTrue();

        request.setCompanyId(10);
        assertThat(validator.isValid(request)).isFalse();
        assertThat(validator.getNumberOfErrors()).isEqualTo(2);
        assertThat(validator.getErrors().contains(CreateEmployeeValidator.FIRSTNAME_REQUIRED)).isTrue();
        assertThat(validator.getErrors().contains(CreateEmployeeValidator.LASTNAME_REQUIRED)).isTrue();
    }

}
