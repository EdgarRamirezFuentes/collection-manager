package com.mycollectionmanager.collectionmanager.services;

import com.mycollectionmanager.collectionmanager.exceptions.user.validationRules.ValidationRuleException;

public interface UserRuleValidatorService {
    public void validate(String field, String value) throws IllegalArgumentException, ValidationRuleException;
}
