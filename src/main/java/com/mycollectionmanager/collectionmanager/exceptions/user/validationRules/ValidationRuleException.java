package com.mycollectionmanager.collectionmanager.exceptions.user.validationRules;

public class ValidationRuleException extends RuntimeException {
    public ValidationRuleException() { super(); }
    public ValidationRuleException(String message) { super(message); }
}
