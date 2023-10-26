package com.mycollectionmanager.collectionmanager.services.impl;

import com.mycollectionmanager.collectionmanager.exceptions.user.validationRules.ValidationRuleException;
import com.mycollectionmanager.collectionmanager.services.UserRuleValidatorService;
import com.mycollectionmanager.collectionmanager.services.validators.UserValidationRule;
import com.mycollectionmanager.collectionmanager.services.validators.impl.PasswordValidationRule;
import com.mycollectionmanager.collectionmanager.services.validators.impl.UsernameValidationRule;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserRuleValidatorServiceImpl implements UserRuleValidatorService {
    private final Map<String, UserValidationRule> validationStrategies = new HashMap<>();
    private  final PasswordValidationRule passwordValidationRule;
    private  final UsernameValidationRule usernameValidationRule;

    public UserRuleValidatorServiceImpl(PasswordValidationRule passwordValidationRule, UsernameValidationRule usernameValidationRule) {
        this.passwordValidationRule = passwordValidationRule;
        this.usernameValidationRule = usernameValidationRule;
        validationStrategies.put("password", passwordValidationRule);
        validationStrategies.put("username", usernameValidationRule);
    }

    /**
     * Validates a specific value using a validation strategy associated with the specified field.
     *
     * @param field The name of the field for which validation is applied.
     * @param value The value to be validated.
     * @throws IllegalArgumentException If the specified field is invalid or does not exist in the available validation strategies.
     */
    public void validate(String field, String value) throws IllegalArgumentException, ValidationRuleException {
        UserValidationRule strategy = validationStrategies.get(field);

        if (strategy == null)
            throw new IllegalArgumentException("Invalid validation field: " + field);

        strategy.validate(value);
    }
}
