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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerAndLogin_shouldSucceed() throws Exception {
        var register = new UserRegisterRequest("ramona", "ramona@test.com", "123456");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("ramona"))
                .andExpect(jsonPath("$.email").value("ramona@test.com"));

        var login = new UserLoginRequest("ramona@test.com", "123456");

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("ramona"))
                .andExpect(jsonPath("$.email").value("ramona@test.com"));
    }

    @Test
    void login_withWrongPassword_shouldFail() {
        var register = new UserRegisterRequest("ramona", "ramona2@test.com", "123456");

        assertDoesNotThrow(() ->
                mockMvc.perform(post("/users/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(register)))
                        .andExpect(status().isOk())
        );

        var login = new UserLoginRequest("ramona2@test.com", "wrong");

        Exception exception = assertThrows(Exception.class, () ->
                mockMvc.perform(post("/users/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(login)))
                        .andReturn()
        );

        assertTrue(exception.getCause() instanceof RuntimeException);
        assertEquals("Invalid credentials", exception.getCause().getMessage());
    }
}