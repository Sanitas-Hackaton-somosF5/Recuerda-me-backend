package com.sanitas.recuerdame.user.mapper;

import com.sanitas.recuerdame.user.dto.UserRegisterRequest;
import com.sanitas.recuerdame.user.dto.UserResponse;
import com.sanitas.recuerdame.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRegisterRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(request.password());
        return user;
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }
}