package com.mycollectionmanager.collectionmanager;

import com.mycollectionmanager.collectionmanager.domain.dto.user.UserAuthDTO;
import com.mycollectionmanager.collectionmanager.domain.dto.user.UserRegisterDTO;
import com.mycollectionmanager.collectionmanager.domain.dto.user.UserUpdatePasswordDTO;
import com.mycollectionmanager.collectionmanager.domain.dto.user.UserUpdateUsernameDTO;
import com.mycollectionmanager.collectionmanager.domain.entities.User;

public class TestDataUtils {

    private static final String VALID_USERNAME = "testUser";
    private static final String UPDATED_USERNAME = "updatedUsername";
    private static final String INVALID_USERNAME = "testUser$*";
    private static final String VALID_PASSWORD = "testPassword123$";
    private static final String INVALID_PASSWORD = "invalidPassword";
    private static final String UPDATED_PASSWORD = "updatedPassword123$";
    private static final String EMPTY_VALUE = "";
    private static final String NULL_VALUE = null;

    public static UserRegisterDTO createValidUserRegisterDTOA() {
        return UserRegisterDTO
                .builder()
                .username(VALID_USERNAME)
                .password(VALID_PASSWORD)
                .build();
    }

    public static User createValidUser() {
        return User
                .builder()
                .username(VALID_USERNAME)
                .password(VALID_PASSWORD)
                .build();
    }

    public static UserRegisterDTO createEmptyUsernameUserRegisterDTO() {
        return UserRegisterDTO
                .builder()
                .username(EMPTY_VALUE)
                .password(VALID_PASSWORD)
                .build();
    }

    public static UserRegisterDTO createNullUsernameRegisterDTO() {
        return UserRegisterDTO
                .builder()
                .username(NULL_VALUE)
                .password(VALID_PASSWORD)
                .build();
    }

    public static UserRegisterDTO createEmptyPasswordUserRegisterDTO() {
        return UserRegisterDTO
                .builder()
                .username(VALID_USERNAME)
                .password(EMPTY_VALUE)
                .build();
    }

    public static UserRegisterDTO createInvalidPasswordUserRegisterDTO() {
        return UserRegisterDTO
                .builder()
                .username(VALID_USERNAME)
                .password(INVALID_PASSWORD)
                .build();
    }

    public static UserRegisterDTO createNullPasswordUserRegisterDTO() {
        return UserRegisterDTO
                .builder()
                .username(VALID_USERNAME)
                .password(NULL_VALUE)
                .build();
    }

    public static UserAuthDTO createUserAuthDTO(String username, String password) {
        return UserAuthDTO
                .builder()
                .username(username)
                .password(password)
                .build();
    }

    public static UserUpdateUsernameDTO createUserUpdateUsernameDTO() {
        return UserUpdateUsernameDTO
                .builder()
                .username(UPDATED_USERNAME)
                .build();
    }

    public static UserUpdatePasswordDTO createValidUserUpdatePasswordDTO() {
        return UserUpdatePasswordDTO
                .builder()
                .currentPassword(VALID_PASSWORD)
                .newPassword(UPDATED_PASSWORD)
                .build();
    }

    public static UserUpdatePasswordDTO createEmptyNewPasswordUserUpdatePasswordDTO() {
        return UserUpdatePasswordDTO
                .builder()
                .currentPassword(VALID_PASSWORD)
                .newPassword(EMPTY_VALUE)
                .build();
    }

    public static UserUpdatePasswordDTO createInvalidUserUpdatePasswordDTO() {
        return UserUpdatePasswordDTO
                .builder()
                .currentPassword(VALID_PASSWORD)
                .newPassword(INVALID_PASSWORD)
                .build();
    }

    public static UserUpdatePasswordDTO createIncorrectCurrentPasswordUserUpdatePasswordDTO() {
        return UserUpdatePasswordDTO
                .builder()
                .currentPassword(UPDATED_PASSWORD)
                .newPassword(UPDATED_PASSWORD)
                .build();
    }
}
