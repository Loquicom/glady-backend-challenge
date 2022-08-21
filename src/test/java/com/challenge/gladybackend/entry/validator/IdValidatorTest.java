package com.challenge.gladybackend.entry.validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class IdValidatorTest {

    @Autowired
    private IdValidator validator;

    @Test
    public void contextLoad() {
        assertThat(validator).isNotNull();
    }

    @Test
    public void validTest() {
        assertThat(validator.isValid(1)).isTrue();
        assertThat(validator.isValid(2)).isTrue();
        assertThat(validator.isValid(100)).isTrue();
    }

    @Test
    public void notValidTest() {
        assertThat(validator.isValid(0)).isFalse();
        assertThat(validator.isValid(-1)).isFalse();
    }

}
