package com.mycollectionmanager.collectionmanager.services;

import com.mycollectionmanager.collectionmanager.domain.dto.user.UserRegisterDTO;
import com.mycollectionmanager.collectionmanager.domain.dto.user.UserUpdatePasswordDTO;
import com.mycollectionmanager.collectionmanager.domain.entities.User;
import com.mycollectionmanager.collectionmanager.exceptions.user.UserNotFoundException;
import com.mycollectionmanager.collectionmanager.exceptions.user.validationRules.ValidationRuleException;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User findByUsername(String username);
    void delete(Long id) throws UserNotFoundException;
    User registerUser(User user);
    User updateUsername(User user, String newUsername);
    void updatePassword(User user, String newPassword);
    void validatePasswordChange(User user, UserUpdatePasswordDTO userUpdatePasswordDTO);
    void validateUser(UserRegisterDTO user) throws IllegalArgumentException, ValidationRuleException;
    void validateUsername(String username) throws IllegalArgumentException, ValidationRuleException;
    void validatePassword(String password) throws IllegalArgumentException, ValidationRuleException;
}
