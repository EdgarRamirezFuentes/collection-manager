package com.mycollectionmanager.collectionmanager.exceptions.user.validationRules;

public class UnavailableUsernameException extends ValidationRuleException {
    public UnavailableUsernameException() { super(); }
    public UnavailableUsernameException(String message) { super(message); }
}
