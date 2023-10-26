package com.mycollectionmanager.collectionmanager.exceptions.user.validationRules;

public class InvalidUsernameException extends ValidationRuleException {
    public InvalidUsernameException() { super(); }

    public  InvalidUsernameException(String message) {
        super(message);
    }
}
