package com.mycollectionmanager.collectionmanager.services.impl;

import com.mycollectionmanager.collectionmanager.domain.dto.user.UserRegisterDTO;
import com.mycollectionmanager.collectionmanager.domain.dto.user.UserUpdatePasswordDTO;
import com.mycollectionmanager.collectionmanager.domain.entities.Role;
import com.mycollectionmanager.collectionmanager.domain.entities.User;
import com.mycollectionmanager.collectionmanager.exceptions.user.UserInvalidCredentials;
import com.mycollectionmanager.collectionmanager.exceptions.user.UserNotFoundException;
import com.mycollectionmanager.collectionmanager.exceptions.user.validationRules.ValidationRuleException;
import com.mycollectionmanager.collectionmanager.repositories.UserRepository;
import com.mycollectionmanager.collectionmanager.services.UserRuleValidatorService;
import com.mycollectionmanager.collectionmanager.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRuleValidatorService userRuleValidatorService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserRuleValidatorService userRuleValidatorService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRuleValidatorService = userRuleValidatorService;
    }

    /**
     * Retrieve a list of all users.
     *
     * @return A list of User entities.
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Find a user by their unique ID.
     *
     * @param id The ID of the user to find.
     * @return The User entity if found.
     * @throws UserNotFoundException if the user is not found.
     */
    @Override
    public User findById(Long id) throws UserNotFoundException {
        Optional<User> userRequest = userRepository.findById(id);

        if (userRequest.isEmpty())
            throw new UserNotFoundException("User not found");

        return userRequest.get();
    }

    /**
     * Find a user by their username.
     *
     * @param username The username of the user to find.
     * @return The User entity if found.
     * @throws UserNotFoundException if the user is not found.
     */
    @Override
    public User findByUsername(String username) throws UserNotFoundException {
        Optional<User> userRequest = userRepository.findByUsername(username);

        if (userRequest.isEmpty())
            throw new UserNotFoundException("User not found");

        return userRequest.get();
    }

    /**
     * Delete a user by their ID.
     *
     * @param id The ID of the user to delete.
     */
    @Override
    public void delete(Long id) throws UserNotFoundException {
        if (!userRepository.existsById(id))
            throw new UserNotFoundException("User not found");

        userRepository.deleteById(id);
    }

    /**
     * Register a new user.
     *
     * @param user The UserDTO object containing user details to register.
     * @return The newly registered User entity.
     */
    @Override
    public User registerUser(User user)  {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    /**
     * Update the username of a user.
     *
     * @param user The User entity to update.
     * @param newUsername The new username to set.
     * @return The updated User entity.
     */
    @Override
    public User updateUsername(User user, String newUsername) {
        user.setUsername(newUsername);
        return userRepository.save(user);
    }

    /**
     * Update the password of a user.
     *
     * @param user The User entity to update.
     * @param newPassword The new password to set.
     */
    @Override
    public void updatePassword(User user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    @Override
    public void validatePasswordChange(User user, UserUpdatePasswordDTO userUpdatePasswordDTO) throws ValidationRuleException, UserNotFoundException, UserInvalidCredentials {
        String currentPassword = userUpdatePasswordDTO.getCurrentPassword();
        String newPassword = userUpdatePasswordDTO.getNewPassword();

        validateUserCurrentCredentials(user, currentPassword);
        validatePassword(newPassword);
    }

    /**
     * Validate a UserDTO object according to username and password rules.
     *
     * @param user The UserDTO to validate.
     * @throws IllegalArgumentException if validation fails.
     * @throws ValidationRuleException if validation rules are not met.
     */
    @Override
    public void validateUser(UserRegisterDTO user) throws IllegalArgumentException, ValidationRuleException {
        String username = user.getUsername();
        String password = user.getPassword();

        validateUsername(username);
        validatePassword(password);
    }

    /**
     * Validate the given username against predefined validation rules.
     *
     * @param username The username to validate.
     * @throws IllegalArgumentException if validation fails.
     * @throws ValidationRuleException if validation rules are not met.
     */
    public void validateUsername(String username) throws IllegalArgumentException, ValidationRuleException {
        userRuleValidatorService.validate("username", username);
    }

    /**
     * Validate the given password against predefined validation rules.
     *
     * @param password The password to validate.
     * @throws IllegalArgumentException if validation fails.
     * @throws ValidationRuleException if validation rules are not met.
     */
    public void validatePassword(String password) throws IllegalArgumentException, ValidationRuleException {
        userRuleValidatorService.validate("password", password);
    }

    /**
     * Validate the current credentials of a user by checking if the provided current password
     * matches the user's stored password.
     *
     * @param user The User entity for which credentials are being validated.
     * @param currentPassword The current password provided by the user for validation.
     * @throws UserInvalidCredentials if the current password doesn't match the user's stored password.
     */
    public void validateUserCurrentCredentials(User user, String currentPassword) {
        if (!passwordEncoder.matches(currentPassword, user.getPassword()))
            throw new UserInvalidCredentials("The current password is wrong.");
    }
}
