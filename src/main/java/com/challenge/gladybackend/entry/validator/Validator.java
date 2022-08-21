package com.challenge.gladybackend.entry.validator;

import com.challenge.gladybackend.exception.AppValidatorException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Base validator to validate data send by the user
 *
 * @param <T>
 */
public abstract class Validator<T> {

    private String prefix = "";
    protected List<String> errors = new ArrayList<>();
    protected boolean valid = true;

    /**
     * Validate if the data is valid or not
     * To validate use AssertionValidator class
     *
     * @param data Object to validate
     */
    protected abstract void validate(T data);

    /**
     * Reset validator
     */
    protected void clear() {
        prefix = "";
        valid = true;
        errors.clear();
    }

    /**
     * Get the AssertValidator to verify one field is valid
     * ex; assertThat(name).isNotBLank().valid()
     * <p>
     * To use in the validate method
     *
     * @param data Field from the object T to test
     * @param <D>  Type of the field
     * @return AssertValidator
     */
    protected <D> AssertValidator<D> assertThat(D data) {
        return new AssertValidator<>(this, data);
    }

    /**
     * Check if the data is valid with the validate method
     *
     * @param data Object to validate
     * @return true if is valid, false otherwise
     */
    public boolean isValid(T data) {
        clear();
        validate(data);
        return valid;
    }

    /**
     * Check if a list of data is valid with the validate method
     *
     * @param dataList List of object to validate
     * @return true if is valid, false otherwise
     */
    public boolean isValid(List<T> dataList) {
        clear();
        for (int i = 0; i < dataList.size(); i++) {
            prefix = (i + 1) + ": ";
            validate(dataList.get(i));
        }
        return valid;
    }

    /**
     * Check if the data is valid with the validate method and if not valid throw an exception
     *
     * @param data Object to validate
     * @throws AppValidatorException Send if the result is not valid
     */
    public void isValidOrThrow(T data) throws AppValidatorException {
        isValidOrThrow(data, "Bad request");
    }

    /**
     * Check if the data is valid with the validate method and if not valid throw an exception with a message
     *
     * @param data    Object to validate
     * @param message Exception message
     * @throws AppValidatorException Send if the result is not valid
     */
    public void isValidOrThrow(T data, String message) throws AppValidatorException {
        if (!isValid(data)) {
            throw new AppValidatorException(message, HttpStatus.BAD_REQUEST).trace(this.errors);
        }
    }

    /**
     * Check if a list of data is valid with the validate method and if not valid throw an exception
     *
     * @param data List of object to validate
     * @throws AppValidatorException Send if the result is not valid
     */
    public void isValidOrThrow(List<T> data) throws AppValidatorException {
        isValidOrThrow(data, "Bad request");
    }

    /**
     * Check if a list of data is valid with the validate method and if not valid throw an exception with a message
     *
     * @param data    List of object to validate
     * @param message Exception message
     * @throws AppValidatorException Send if the result is not valid
     */
    public void isValidOrThrow(List<T> data, String message) throws AppValidatorException {
        if (!isValid(data)) {
            throw new AppValidatorException(message, HttpStatus.BAD_REQUEST).trace(this.errors);
        }
    }

    public int getNumberOfErrors() {
        return this.errors.size();
    }

    public String getError(int index) {
        if (index < 0 || index >= errors.size()) {
            throw new IllegalArgumentException("Invalid index: " + index);
        }
        return errors.get(index);
    }

    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    /* ---       Sub class AssertValidator       --*/
    /* Use in the validate method to validate data */

    class AssertValidator<D> {

        private final Validator<T> validator;
        private final D data;
        private boolean valid = true;

        public AssertValidator(Validator<T> validator, D data) {
            this.validator = validator;
            this.data = data;
        }

        /**
         * Check if the data is null
         *
         * @return this
         */
        public AssertValidator<D> isNull() {
            if (data != null) {
                valid = false;
            }
            return this;
        }

        /**
         * Check if the data is not null
         *
         * @return this
         */
        public AssertValidator<D> isNotNull() {
            if (data == null) {
                valid = false;
            }
            return this;
        }

        /**
         * Check if the data is equals
         *
         * @param obj Object to compare
         * @return this
         */
        public AssertValidator<D> isEquals(Object obj) {
            // If data is null check if the other object is null
            if (data == null) {
                if (obj != null) {
                    valid = false;
                }
                return this;
            }
            // Check if object are equals
            if (!data.equals(obj)) {
                valid = false;
            }
            return this;
        }

        /**
         * Check if the data is not equals
         *
         * @param obj Object to compare
         * @return this
         */
        public AssertValidator<D> isNotEquals(Object obj) {
            // If data is null check if the other object is null
            if (data == null) {
                if (obj == null) {
                    valid = false;
                }
                return this;
            }
            // Check if object are equals
            if (data.equals(obj)) {
                valid = false;
            }
            return this;
        }

        /**
         * Check if the data is blank
         *
         * @return this
         */
        public AssertValidator<D> isBlank() {
            // Check if is boolean type and cast
            if (!(data instanceof String s)) {
                valid = false;
                return this;
            }
            // Check if is blank
            if (!s.isBlank()) {
                valid = false;
            }
            return this;
        }

        /**
         * Check if the data is not blank
         *
         * @return this
         */
        public AssertValidator<D> isNotBlank() {
            // Check if is boolean type and cast
            if (!(data instanceof String s)) {
                valid = false;
                return this;
            }
            // Check if is blank
            if (s.isBlank()) {
                valid = false;
            }
            return this;
        }

        /**
         * Check if the data is true
         *
         * @return this
         */
        public AssertValidator<D> isTrue() {
            // Check if is boolean type and cast
            if (!(data instanceof Boolean b)) {
                valid = false;
                return this;
            }
            // Check value
            if (!b) {
                valid = false;
            }
            return this;
        }

        /**
         * Check if the data is false
         *
         * @return this
         */
        public AssertValidator<D> isFalse() {
            // Check if is boolean type and cast
            if (!(data instanceof Boolean b)) {
                valid = false;
                return this;
            }
            // Check value
            if (b) {
                valid = false;
            }
            return this;
        }

        /**
         * Update the parent Validator state with the AsserValidator result
         */
        public void valid() {
            validator.valid = validator.valid && this.valid;
        }

        /**
         * Update the parent Validator state with the AsserValidator result and add message in errors list if it's not valid
         *
         * @param message Error message
         */
        public void orError(String message) {
            valid();
            if (!valid) {
                validator.errors.add(validator.prefix + message);
            }
        }

        /**
         * Update the parent Validator state with the AsserValidator result and throw an exception if it's not valid
         *
         * @throws AppValidatorException Send is date is not valid
         */
        public void orThrow() throws AppValidatorException {
            orThrow("Incorrect value");
        }

        /**
         * Update the parent Validator state with the AsserValidator result and throw an exception if it's not valid
         *
         * @param message Exception message
         * @throws AppValidatorException Send is date is not valid
         */
        public void orThrow(String message) throws AppValidatorException {
            valid();
            if (!valid) {
                throw new AppValidatorException(message, HttpStatus.BAD_REQUEST);
            }
        }

    }

}
