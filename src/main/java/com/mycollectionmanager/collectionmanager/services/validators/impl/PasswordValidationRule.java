package com.mycollectionmanager.collectionmanager.services.validators.impl;

import com.mycollectionmanager.collectionmanager.exceptions.user.validationRules.InvalidPasswordException;
import com.mycollectionmanager.collectionmanager.exceptions.user.validationRules.ValidationRuleException;
import com.mycollectionmanager.collectionmanager.services.validators.UserValidationRule;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PasswordValidationRule implements UserValidationRule {
    private final String PASSWORD_REGEX= "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$_\\-!%*?&])[A-Za-z\\d@$_\\-!%*?&]{8,}$";

    /**
     * Validates a password based on a regular expression pattern that requires:
     * - At least one lowercase letter.
     * - At least one uppercase letter.
     * - At least one digit.
     * - At least one special character among @, $, _, -, !, %, *, ?, and &.
     * - A minimum length of 8 characters.
     *
     * @param password The password to validate.
     * @return true if the password matches the pattern, false otherwise.
     */
    private boolean validatePassword(final String password) {
        Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);
        Matcher passwordMatcher = passwordPattern.matcher(password);
        return passwordMatcher.matches();
    }

    @Override
    public void validate(String password) throws ValidationRuleException {
        if (password == null)
            throw new InvalidPasswordException("The password must not be null");

        if (!validatePassword(password))
            throw new InvalidPasswordException("The password does not fulfill the requirements.");
    }
}
