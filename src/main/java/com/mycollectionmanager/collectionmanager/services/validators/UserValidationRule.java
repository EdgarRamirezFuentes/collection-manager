package com.mycollectionmanager.collectionmanager.services.validators;

import com.mycollectionmanager.collectionmanager.exceptions.user.validationRules.ValidationRuleException;

public interface UserValidationRule {
    void validate(String value) throws ValidationRuleException;
}
