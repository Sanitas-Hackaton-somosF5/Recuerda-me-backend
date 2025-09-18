
package com.sanitas.recuerdame.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanitas.recuerdame.user.dto.UserLoginRequest;
import com.sanitas.recuerdame.user.dto.UserRegisterRequest;
import com.sanitas.recuerdame.user.dto.UserResponse;
import com.sanitas.recuerdame.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void register_shouldReturnUserResponse() throws Exception {
        var registerRequest = new UserRegisterRequest("ramona", "ramona@test.com", "123456");
        var response = new UserResponse(1L, "ramona", "ramona@test.com");

        Mockito.when(userService.register(registerRequest)).thenReturn(response);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("ramona"))
                .andExpect(jsonPath("$.email").value("ramona@test.com"));
    }

    @Test
    void login_shouldReturnUserResponse() throws Exception {
        var loginRequest = new UserLoginRequest("ramona@test.com", "123456");
        var response = new UserResponse(1L, "ramona", "ramona@test.com");

        Mockito.when(userService.login(loginRequest)).thenReturn(response);

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("ramona"))
                .andExpect(jsonPath("$.email").value("ramona@test.com"));
    }
}