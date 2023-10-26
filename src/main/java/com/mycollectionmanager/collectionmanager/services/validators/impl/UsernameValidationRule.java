package com.mycollectionmanager.collectionmanager.services.validators.impl;

import com.mycollectionmanager.collectionmanager.exceptions.user.validationRules.InvalidUsernameException;
import com.mycollectionmanager.collectionmanager.exceptions.user.validationRules.UnavailableUsernameException;
import com.mycollectionmanager.collectionmanager.exceptions.user.validationRules.ValidationRuleException;
import com.mycollectionmanager.collectionmanager.repositories.UserRepository;
import com.mycollectionmanager.collectionmanager.services.validators.UserValidationRule;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UsernameValidationRule implements UserValidationRule {

    private final UserRepository userRepository;
    private final String USERNAME_REGEX = "\\w{5,}";

    public UsernameValidationRule(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Validates a username based on a regular expression pattern that requires:
     * - A minimum length of 5 characters.
     * - It contains only word characters: [a-zA-Z_0-9]
     *
     * @param username The username to validate.
     * @return true if the username matches the pattern, false otherwise.
     */
    private boolean validateUsername(String username) {
        Pattern usernamePattern = Pattern.compile(USERNAME_REGEX);
        Matcher usernameMatcher = usernamePattern.matcher(username);
        return usernameMatcher.matches();
    }

    /**
     * Checks the availability of a username by querying the repository.
     *
     * @param username The username to check for availability.
     * @return true if the username is available, false if it already exists.
     */
    private boolean validateAvailability(String username) {
        return !userRepository.existsUserByUsername(username);
    }

    @Override
    public void validate(String username) throws ValidationRuleException {
        if (username == null)
            throw new InvalidUsernameException("The user must not be null");

        if (!validateUsername(username))
            throw new InvalidUsernameException("The user does not fulfill the requirements.");

        if (!validateAvailability(username))
            throw new UnavailableUsernameException("The username is not available");
    }
}
