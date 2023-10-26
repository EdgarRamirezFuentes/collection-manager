package com.mycollectionmanager.collectionmanager.exceptions.user;

public class UserInvalidCredentials extends RuntimeException {
    public UserInvalidCredentials() { super(); }
    public UserInvalidCredentials(String message) { super(message); }
}
