package com.sanitas.recuerdame.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanitas.recuerdame.user.dto.UserLoginRequest;
import com.sanitas.recuerdame.user.dto.UserRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerAndLogin_shouldSucceed() throws Exception {
        var register = new UserRegisterRequest("sofia", "sofia@test.com", "123456");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("sofia"))
                .andExpect(jsonPath("$.email").value("sofia@test.com"));

        var login = new UserLoginRequest("sofia@test.com", "123456");

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("sofia"))
                .andExpect(jsonPath("$.email").value("sofia@test.com"));
    }

    @Test
    void register_withValidData_shouldReturnCreated() throws Exception {
        var register = new UserRegisterRequest("elena", "elena@test.com", "password123");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("elena"))
                .andExpect(jsonPath("$.email").value("elena@test.com"));
    }

    @Test
    void login_withWrongPassword_shouldReturnUnauthorized() throws Exception {
        var register = new UserRegisterRequest("laura", "laura@test.com", "correctPassword");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isCreated());

        var login = new UserLoginRequest("laura@test.com", "wrongPassword");

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid credentials"))
                .andExpect(jsonPath("$.status").value(401));
    }

    @Test
    void login_withNonExistentUser_shouldReturnUnauthorized() throws Exception {
        var login = new UserLoginRequest("nonexistent@test.com", "123456");

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid credentials"))
                .andExpect(jsonPath("$.status").value(401));
    }

    @Test
    void register_withDuplicateEmail_shouldReturnConflict() throws Exception {
        var firstRegister = new UserRegisterRequest("maria", "duplicate@test.com", "123456");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(firstRegister)))
                .andExpect(status().isCreated());

        var duplicateRegister = new UserRegisterRequest("ana", "duplicate@test.com", "654321");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateRegister)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Email already exists"));
    }

    @Test
    void register_withInvalidData_shouldReturnBadRequest() throws Exception {
        var invalidRegister = new UserRegisterRequest("", "email-invalido", "");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRegister)))
                .andExpect(status().isBadRequest()); // 400
    }
}