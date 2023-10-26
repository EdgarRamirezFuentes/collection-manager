package com.mycollectionmanager.collectionmanager.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycollectionmanager.collectionmanager.TestDataUtils;
import com.mycollectionmanager.collectionmanager.domain.dto.user.UserAuthDTO;
import com.mycollectionmanager.collectionmanager.domain.dto.user.UserRegisterDTO;
import com.mycollectionmanager.collectionmanager.domain.dto.user.UserUpdatePasswordDTO;
import com.mycollectionmanager.collectionmanager.domain.dto.user.UserUpdateUsernameDTO;
import com.mycollectionmanager.collectionmanager.domain.entities.User;
import com.mycollectionmanager.collectionmanager.services.JwtService;
import com.mycollectionmanager.collectionmanager.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {

    private final UserService userService;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserControllerIntegrationTests(UserService userService, MockMvc mockMvc, ObjectMapper objectMapper, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Test
    public void testThatCreateUserSuccessfullyReturnsHttp201Created() throws Exception {
        UserRegisterDTO testUser = TestDataUtils.createValidUserRegisterDTOA();

        String testUserJson = objectMapper.writeValueAsString(testUser);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testUserJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateUserSuccessfullyReturnsSavedUserAndToken() throws Exception {
        UserRegisterDTO testUser = TestDataUtils.createValidUserRegisterDTOA();

        String testUserJson = objectMapper.writeValueAsString(testUser);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testUserJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.username").value(testUser.getUsername())
        );
    }

    @Test
    public void testThatCreateUserWithEmptyUsernameReturnsHttp400() throws Exception {
        UserRegisterDTO testUser = TestDataUtils.createEmptyUsernameUserRegisterDTO();

        String testUserJson = objectMapper.writeValueAsString(testUser);

        mockMvc.perform(
        MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testUserJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    public void testThatCreateUserWithNullUsernameReturnsHttp400() throws Exception {
        UserRegisterDTO testUser = TestDataUtils.createNullUsernameRegisterDTO();

        String testUserJson = objectMapper.writeValueAsString(testUser);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testUserJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    public void testThatCreateUserWithAlreadyRegisteredUsernameReturnsHttp400() throws Exception {
        UserRegisterDTO testUserA = TestDataUtils.createValidUserRegisterDTOA();
        String testUserAJson = objectMapper.writeValueAsString(testUserA);

        User newUser = User
                        .builder()
                        .username(testUserA.getUsername())
                        .password(testUserA.getPassword())
                        .build();

        userService.registerUser(newUser);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testUserAJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    public void testThatCreateUserWithEmptyPasswordReturnsHttp400() throws Exception {
        UserRegisterDTO testUser = TestDataUtils.createEmptyPasswordUserRegisterDTO();

        String testUserJson = objectMapper.writeValueAsString(testUser);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testUserJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    public void testThatCreateUserWithNullPasswordReturnsHttp400() throws Exception {
        UserRegisterDTO testUser = TestDataUtils.createNullPasswordUserRegisterDTO();

        String testUserJson = objectMapper.writeValueAsString(testUser);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testUserJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    public void testThatCreateUserWithInvalidPasswordReturnsHttp400() throws Exception {
        UserRegisterDTO testUser = TestDataUtils.createInvalidPasswordUserRegisterDTO();

        String testUserJson = objectMapper.writeValueAsString(testUser);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testUserJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test void testThatAuthenticateUserSuccessfullyReturnsHttp200() throws Exception{
        User testUser = TestDataUtils.createValidUser();
        userService.registerUser(testUser);

        testUser = TestDataUtils.createValidUser();

        UserAuthDTO userCredentials = TestDataUtils.createUserAuthDTO(testUser.getUsername(), testUser.getPassword());
        String userCredentialsJson = objectMapper.writeValueAsString(userCredentials);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCredentialsJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.token").exists()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.username").value(userCredentials.getUsername())
        );
    }

    @Test
    void testThatAuthenticateUserWithWrongCredentialsReturnsHttp400() throws Exception{
        User testUser = TestDataUtils.createValidUser();
        userService.registerUser(testUser);

        UserAuthDTO userCredentials = TestDataUtils.createUserAuthDTO(testUser.getUsername(), "wrongPassword");
        String userCredentialsJson = objectMapper.writeValueAsString(userCredentials);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userCredentialsJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    public void testThatSuccessfulUsernameChangeReturnsHttp200() throws Exception{
        User testUser = TestDataUtils.createValidUser();
        final Authentication credentials = new UsernamePasswordAuthenticationToken(testUser.getUsername(), testUser.getPassword());

        userService.registerUser(testUser);
        authenticationManager.authenticate(credentials);
        String token = "Bearer " + jwtService.getToken(testUser);

        UserUpdateUsernameDTO userUpdateUsernameDTO = TestDataUtils.createUserUpdateUsernameDTO();
        String userUpdateUsernameDTOJson = objectMapper.writeValueAsString(userUpdateUsernameDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/users/change-username/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUpdateUsernameDTOJson)
                        .header("Authorization", token)

        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void TestThatUnavailableUsernameChangeReturnsHttp400() throws Exception {
        User testUser = TestDataUtils.createValidUser();
        User testUserB = TestDataUtils.createValidUser();
        testUserB.setUsername("testUserB");

        final Authentication credentials = new UsernamePasswordAuthenticationToken(testUser.getUsername(), testUser.getPassword());

        userService.registerUser(testUser);
        userService.registerUser(testUserB);

        authenticationManager.authenticate(credentials);
        String token = "Bearer " + jwtService.getToken(testUser);

        UserUpdateUsernameDTO userUpdateUsernameDTO = TestDataUtils.createUserUpdateUsernameDTO();
        userUpdateUsernameDTO.setUsername(testUserB.getUsername());

        String userUpdateUsernameDTOJson = objectMapper.writeValueAsString(userUpdateUsernameDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/users/change-username/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUpdateUsernameDTOJson)
                        .header("Authorization", token)

        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    public void testThatSuccessfulUsernameChangeReturnNewAuthToken() throws Exception {
        User testUser = TestDataUtils.createValidUser();
        final Authentication credentials = new UsernamePasswordAuthenticationToken(testUser.getUsername(), testUser.getPassword());

        userService.registerUser(testUser);
        authenticationManager.authenticate(credentials);
        String token = "Bearer " + jwtService.getToken(testUser);

        UserUpdateUsernameDTO userUpdateUsernameDTO = TestDataUtils.createUserUpdateUsernameDTO();
        String userUpdateUsernameDTOJson = objectMapper.writeValueAsString(userUpdateUsernameDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/users/change-username/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUpdateUsernameDTOJson)
                        .header("Authorization", token)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.token").exists()
        );
    }

    @Test
    public void testThatSuccessfulPasswordChangeReturnsHttp204() throws Exception {
        User testUser = TestDataUtils.createValidUser();
        final Authentication credentials = new UsernamePasswordAuthenticationToken(testUser.getUsername(), testUser.getPassword());

        userService.registerUser(testUser);
        authenticationManager.authenticate(credentials);
        String token = "Bearer " + jwtService.getToken(testUser);

        UserUpdatePasswordDTO userUpdateUsernameDTO = TestDataUtils.createValidUserUpdatePasswordDTO();
        String userUpdateUsernameDTOJson = objectMapper.writeValueAsString(userUpdateUsernameDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/users/change-password/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUpdateUsernameDTOJson)
                        .header("Authorization", token)

        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatEmptyNewPasswordPasswordChangeReturnsHttp400() throws Exception {
        User testUser = TestDataUtils.createValidUser();
        final Authentication credentials = new UsernamePasswordAuthenticationToken(testUser.getUsername(), testUser.getPassword());

        userService.registerUser(testUser);
        authenticationManager.authenticate(credentials);
        String token = "Bearer " + jwtService.getToken(testUser);

        UserUpdatePasswordDTO userUpdateUsernameDTO = TestDataUtils.createEmptyNewPasswordUserUpdatePasswordDTO();
        String userUpdateUsernameDTOJson = objectMapper.writeValueAsString(userUpdateUsernameDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/users/change-password/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUpdateUsernameDTOJson)
                        .header("Authorization", token)

        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    public void testThatInvalidNewPasswordPasswordChangeReturnsHttp400() throws Exception {
        User testUser = TestDataUtils.createValidUser();
        final Authentication credentials = new UsernamePasswordAuthenticationToken(testUser.getUsername(), testUser.getPassword());

        userService.registerUser(testUser);
        authenticationManager.authenticate(credentials);
        String token = "Bearer " + jwtService.getToken(testUser);

        UserUpdatePasswordDTO userUpdateUsernameDTO = TestDataUtils.createInvalidUserUpdatePasswordDTO();
        String userUpdateUsernameDTOJson = objectMapper.writeValueAsString(userUpdateUsernameDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/users/change-password/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUpdateUsernameDTOJson)
                        .header("Authorization", token)

        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    public void testThatIncorrectCurrentPasswordPasswordChangeReturnsHttp400() throws Exception {
        User testUser = TestDataUtils.createValidUser();
        final Authentication credentials = new UsernamePasswordAuthenticationToken(testUser.getUsername(), testUser.getPassword());

        userService.registerUser(testUser);
        authenticationManager.authenticate(credentials);
        String token = "Bearer " + jwtService.getToken(testUser);

        UserUpdatePasswordDTO userUpdateUsernameDTO = TestDataUtils.createIncorrectCurrentPasswordUserUpdatePasswordDTO();
        String userUpdateUsernameDTOJson = objectMapper.writeValueAsString(userUpdateUsernameDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/users/change-password/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userUpdateUsernameDTOJson)
                        .header("Authorization", token)

        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }
}
