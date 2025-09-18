package com.sanitas.recuerdame.user.service;

import com.sanitas.recuerdame.user.dto.UserLoginRequest;
import com.sanitas.recuerdame.user.dto.UserRegisterRequest;
import com.sanitas.recuerdame.user.dto.UserResponse;

public interface UserService {
    UserResponse register(UserRegisterRequest request);
    UserResponse login(UserLoginRequest request);
}
