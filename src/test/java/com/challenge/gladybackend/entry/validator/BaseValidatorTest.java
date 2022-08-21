package com.challenge.gladybackend.entry.validator;

import com.challenge.gladybackend.exception.AppValidatorException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class BaseValidatorTest extends Validator<Object> {

    private static final String ERROR_MESSAGE = "error message";

    private boolean validate = true;

    @BeforeEach
    public void beforeEach() {
        validate = true;
        clear();
    }

    @Test
    public void clearTest() {
        Assertions.assertThat(valid).isTrue();
        Assertions.assertThat(errors.size()).isEqualTo(0);
        assertThat(false).isTrue().orError("test");
        Assertions.assertThat(valid).isFalse();
        Assertions.assertThat(errors.size()).isEqualTo(1);
        clear();
        Assertions.assertThat(valid).isTrue();
        Assertions.assertThat(errors.size()).isEqualTo(0);
    }

    @Test
    public void isValidTest() {
        Assertions.assertThat(isValid("test")).isTrue();
        validate = false;
        Assertions.assertThat(isValid("test")).isFalse();
    }

    @Test
    public void isValidListTest() {
        List<String> array = Arrays.asList("test-1", "test-2");
        Assertions.assertThat(isValid(array)).isTrue();
        validate = false;
        Assertions.assertThat(isValid(array)).isFalse();
    }

    @Test
    public void isValidOrThrowTest() throws AppValidatorException {
        isValidOrThrow("test", ERROR_MESSAGE);
        validate = false;
        AppValidatorException ex = assertThrows(AppValidatorException.class, () -> isValidOrThrow("test", ERROR_MESSAGE));
        Assertions.assertThat(ex.getMessage()).isEqualTo(ERROR_MESSAGE);
        Assertions.assertThat(ex.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void isValidOrThrowListTest() throws AppValidatorException {
        List<String> array = Arrays.asList("test-1", "test-2");
        isValidOrThrow(array, ERROR_MESSAGE);
        validate = false;
        AppValidatorException ex = assertThrows(AppValidatorException.class, () -> isValidOrThrow(array, ERROR_MESSAGE));
        Assertions.assertThat(ex.getMessage()).isEqualTo(ERROR_MESSAGE);
        Assertions.assertThat(ex.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void getNumberOfErrorsTest() {
        Assertions.assertThat(getNumberOfErrors()).isEqualTo(0);
        assertThat(false).isTrue().valid();
        Assertions.assertThat(getNumberOfErrors()).isEqualTo(0);
        assertThat(false).isTrue().orError("test-1");
        Assertions.assertThat(getNumberOfErrors()).isEqualTo(1);
        assertThat(false).isTrue().orError("test-2");
        assertThat(false).isTrue().orError("test-3");
        Assertions.assertThat(getNumberOfErrors()).isEqualTo(3);
        clear();
        Assertions.assertThat(getNumberOfErrors()).isEqualTo(0);
    }

    @Test
    public void getErrorTest() {
        assertThrows(IllegalArgumentException.class, () -> getError(0));
        assertThat(false).isTrue().orError("test-1");
        assertThat(false).isTrue().orError("test-2");
        Assertions.assertThat(getError(0)).isEqualTo("test-1");
        Assertions.assertThat(getError(1)).isEqualTo("test-2");
        assertThrows(IllegalArgumentException.class, () -> getError(2));
        assertThrows(IllegalArgumentException.class, () -> getError(-1));
    }

    @Test
    public void getErrorsTest() {
        Assertions.assertThat(getErrors()).isEqualTo(Collections.EMPTY_LIST);
        assertThat(false).isTrue().orError("test-1");
        assertThat(false).isTrue().orError("test-2");
        List<String> errors = getErrors();
        Assertions.assertThat(errors.size()).isEqualTo(2);
        Assertions.assertThat(errors.contains("test-1")).isTrue();
        Assertions.assertThat(errors.contains("test-2")).isTrue();
    }

    @Test
    public void assertIsNullTest() {
        assertThat(null).isNull().valid();
        Assertions.assertThat(this.valid).isTrue();
        clear();
        assertThat("test").isNull().valid();
        Assertions.assertThat(this.valid).isFalse();
    }

    @Test
    public void assertIsNotNullTest() {
        assertThat(null).isNotNull().valid();
        Assertions.assertThat(this.valid).isFalse();
        clear();
        assertThat("test").isNotNull().valid();
        Assertions.assertThat(this.valid).isTrue();
    }

    @Test
    public void assertIsEqualsTest() {
        assertThat("test").isEquals("test").valid();
        Assertions.assertThat(this.valid).isTrue();
        clear();
        assertThat("test").isEquals("not test").valid();
        Assertions.assertThat(this.valid).isFalse();
        clear();
        assertThat(null).isEquals("test").valid();
        Assertions.assertThat(this.valid).isFalse();
    }

    @Test
    public void assertIsNotEqualsTest() {
        assertThat("test").isNotEquals("test").valid();
        Assertions.assertThat(this.valid).isFalse();
        clear();
        assertThat("test").isNotEquals("not test").valid();
        Assertions.assertThat(this.valid).isTrue();
        clear();
        assertThat(null).isEquals("test").valid();
        Assertions.assertThat(this.valid).isFalse();
    }

    @Test
    public void assertIsBlankTest() {
        assertThat("").isBlank().valid();
        Assertions.assertThat(this.valid).isTrue();
        clear();
        assertThat("                ").isBlank().valid();
        Assertions.assertThat(this.valid).isTrue();
        clear();
        assertThat("test").isBlank().valid();
        Assertions.assertThat(this.valid).isFalse();
        clear();
        assertThat(null).isBlank().valid();
        Assertions.assertThat(this.valid).isFalse();
        clear();
        assertThat(new Object()).isBlank().valid();
        Assertions.assertThat(this.valid).isFalse();
    }

    @Test
    public void assertIsNotBlankTest() {
        assertThat("").isNotBlank().valid();
        Assertions.assertThat(this.valid).isFalse();
        clear();
        assertThat("                ").isNotBlank().valid();
        Assertions.assertThat(this.valid).isFalse();
        clear();
        assertThat("test").isNotBlank().valid();
        Assertions.assertThat(this.valid).isTrue();
        clear();
        assertThat(null).isNotBlank().valid();
        Assertions.assertThat(this.valid).isFalse();
        clear();
        assertThat(new Object()).isNotBlank().valid();
        Assertions.assertThat(this.valid).isFalse();
    }

    @Test
    public void assertIsTrueTest() {
        assertThat(true).isTrue().valid();
        Assertions.assertThat(this.valid).isTrue();
        clear();
        assertThat(false).isTrue().valid();
        Assertions.assertThat(this.valid).isFalse();
        clear();
        assertThat("test").isTrue().valid();
        Assertions.assertThat(this.valid).isFalse();
    }

    @Test
    public void assertIsFalseTest() {
        assertThat(true).isFalse().valid();
        Assertions.assertThat(this.valid).isFalse();
        clear();
        assertThat(false).isFalse().valid();
        Assertions.assertThat(this.valid).isTrue();
        clear();
        assertThat("test").isFalse().valid();
        Assertions.assertThat(this.valid).isFalse();
    }

    @Test
    public void validTest() {
        Assertions.assertThat(valid).isTrue();
        assertThat(true).isTrue().valid();
        Assertions.assertThat(valid).isTrue();
        assertThat(false).isTrue().valid();
        Assertions.assertThat(valid).isFalse();
    }

    @Test
    public void assertOrError() {
        assertThat(true).isTrue().orError("test-1");
        assertThat(false).isTrue().orError("test-2");
        assertThat(false).isTrue().orError("test-3");
        Assertions.assertThat(errors.size()).isEqualTo(2);
        Assertions.assertThat(errors.contains("test-1")).isFalse();
        Assertions.assertThat(errors.contains("test-2")).isTrue();
        Assertions.assertThat(errors.contains("test-3")).isTrue();
    }

    @Test
    public void assertOrThrow() throws AppValidatorException {
        assertThat(true).isTrue().orThrow(ERROR_MESSAGE);
        clear();
        AppValidatorException ex = assertThrows(AppValidatorException.class, () -> assertThat(false).isTrue().orThrow(ERROR_MESSAGE));
        Assertions.assertThat(ex.getMessage()).isEqualTo(ERROR_MESSAGE);
        Assertions.assertThat(ex.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Override
    public void validate(Object data) {
        assertThat(validate).isTrue().valid();
    }
}
