package com.sanitas.recuerdame.user.services;

import com.sanitas.recuerdame.user.dtos.UserRequest;
import com.sanitas.recuerdame.user.dtos.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    void deleteUser(Long id);
}