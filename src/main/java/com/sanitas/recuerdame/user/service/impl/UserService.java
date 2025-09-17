package com.sanitas.recuerdame.user.service.impl;

import com.sanitas.recuerdame.user.dto.UserRequest;
import com.sanitas.recuerdame.user.dto.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserResponse createUser(UserRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    void deleteUser(Long id);

    Optional<UserResponse> login(UserRequest request);
}
