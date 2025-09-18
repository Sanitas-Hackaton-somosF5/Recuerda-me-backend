package com.sanitas.recuerdame.user.service;

import com.sanitas.recuerdame.user.dto.UserLoginRequest;
import com.sanitas.recuerdame.user.dto.UserRegisterRequest;
import com.sanitas.recuerdame.user.dto.UserResponse;
import com.sanitas.recuerdame.user.entity.User;
import com.sanitas.recuerdame.user.mapper.UserMapper;
import com.sanitas.recuerdame.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse register(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }
        User user = userMapper.toEntity(request);
        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    @Override
    public UserResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmailAndPassword(request.email(), request.password())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        return userMapper.toResponse(user);
    }
}


