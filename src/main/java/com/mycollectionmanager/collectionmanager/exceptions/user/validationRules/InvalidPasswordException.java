package com.mycollectionmanager.collectionmanager.exceptions.user.validationRules;

public class InvalidPasswordException extends ValidationRuleException {
    public InvalidPasswordException() { super(); }

    public  InvalidPasswordException(String message) {
        super(message);
    }
}
