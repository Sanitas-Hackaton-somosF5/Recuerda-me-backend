
package com.sanitas.recuerdame.user.controller;

import com.sanitas.recuerdame.user.dto.UserRequest;
import com.sanitas.recuerdame.user.dto.UserResponse;
import com.sanitas.recuerdame.user.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest request) {
        return userService.login(request)
                .map(ResponseEntity::ok) // 200 OK si encuentra usuario válido
                .orElseGet(() -> ResponseEntity.status(401).build());
    }
}