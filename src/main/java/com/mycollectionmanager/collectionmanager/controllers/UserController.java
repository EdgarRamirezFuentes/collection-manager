package com.mycollectionmanager.collectionmanager.controllers;

import com.mycollectionmanager.collectionmanager.domain.dto.user.*;
import com.mycollectionmanager.collectionmanager.domain.entities.User;
import com.mycollectionmanager.collectionmanager.exceptions.user.UserInvalidCredentials;
import com.mycollectionmanager.collectionmanager.exceptions.user.UserNotFoundException;
import com.mycollectionmanager.collectionmanager.exceptions.user.validationRules.ValidationRuleException;
import com.mycollectionmanager.collectionmanager.mappers.user.UserDetailMapper;
import com.mycollectionmanager.collectionmanager.services.JwtService;
import com.mycollectionmanager.collectionmanager.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final UserDetailMapper userDetailMapper;

    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService,
                          JwtService jwtService,
                          UserDetailMapper userDetailMapper,
                          AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.userDetailMapper = userDetailMapper;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("")
    public ResponseEntity<?> listUsers() {
        try {
            List<User> users = userService.findAll();
            List<UserDetailDTO> userList = users.stream()
                    .map(userDetailMapper::mapTo)
                    .toList();

            return ResponseEntity.status(200).body(userList);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Server error.");
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable final Long id) {
        try {
            User user = userService.findById(id);

            return ResponseEntity.status(200).body(user);
        } catch (UserNotFoundException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(400).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Server error.");
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            userService.validateUser(userRegisterDTO);
            User newUser = User
                    .builder()
                    .username(userRegisterDTO.getUsername())
                    .password(userRegisterDTO.getPassword())
                    .build();
            User registeredUser = userService.registerUser(newUser);
            return ResponseEntity.status(201).body(userDetailMapper.mapTo(registeredUser));
        } catch (ValidationRuleException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(400).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Server error.");
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<?> login(@RequestBody final UserAuthDTO userAuthDTO) {
        try {
            final String username = userAuthDTO.getUsername();
            final String password = userAuthDTO.getPassword();

            final Authentication credentials = new UsernamePasswordAuthenticationToken(username, password);

            authenticationManager.authenticate(credentials);

            User user = userService.findByUsername(username);

            UserAuthTokenDTO response = UserAuthTokenDTO
                    .builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .token(jwtService.getToken(user))
                    .build();
            return ResponseEntity.status(200).body(response);
        } catch(BadCredentialsException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "The credentials are wrong");
            return ResponseEntity.status(400).body(response);
        } catch (UserNotFoundException | InternalAuthenticationServiceException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(400).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Server error.");
            return ResponseEntity.status(500).body(response);
        }
    }

    @PatchMapping("/change-username/{id}")
    public ResponseEntity<?> changeUsername(@PathVariable final Long id,
                                            @RequestBody final UserUpdateUsernameDTO userUpdateUsernameDTO) {
        try {
            String newUsername = userUpdateUsernameDTO.getUsername();
            userService.validateUsername(newUsername);

            User user = userService.findById(id);
            User updatedUser = userService.updateUsername(user, newUsername);

            jwtService.getToken(updatedUser);

            UserAuthTokenDTO response = UserAuthTokenDTO
                    .builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .token(jwtService.getToken(user))
                    .build();
            return ResponseEntity.status(200).body(response);
        }  catch (ValidationRuleException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(400).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Server error.");
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PatchMapping("/change-password/{id}")
    public ResponseEntity<?> changePassword(@PathVariable Long id,
                                           @RequestBody UserUpdatePasswordDTO userUpdatePasswordDTO) {
        try {

            User user = userService.findById(id);
            userService.validatePasswordChange(user, userUpdatePasswordDTO);
            userService.updatePassword(user, userUpdatePasswordDTO.getNewPassword());

            return ResponseEntity.noContent().build();
        }  catch (ValidationRuleException | UserInvalidCredentials e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(400).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Server error.");
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body(response);
        }

    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable final Long id) {
        try {
            userService.delete(id);

            return ResponseEntity.noContent().build();
        } catch(UserNotFoundException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.status(400).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Server error.");
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
