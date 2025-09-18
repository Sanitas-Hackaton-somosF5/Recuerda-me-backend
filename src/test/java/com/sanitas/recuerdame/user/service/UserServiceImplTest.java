package com.sanitas.recuerdame.user.service;

import com.sanitas.recuerdame.user.dto.UserLoginRequest;
import com.sanitas.recuerdame.user.dto.UserRegisterRequest;
import com.sanitas.recuerdame.user.entity.User;
import com.sanitas.recuerdame.user.mapper.UserMapper;
import com.sanitas.recuerdame.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceImplTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserMapper userMapper = new UserMapper();

    private final UserServiceImpl userService = new UserServiceImpl(userRepository, userMapper);

    @Test
    void register_shouldSaveAndReturnUserResponse() {
        UserRegisterRequest request = new UserRegisterRequest("ramona", "ramona@test.com", "123456");
        User saved = userMapper.toEntity(request);
        saved.setId(1L);

        Mockito.when(userRepository.existsByEmail("ramona@test.com")).thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(saved);

        var response = userService.register(request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.username()).isEqualTo("ramona");
    }

    @Test
    void login_withValidCredentials_shouldReturnUserResponse() {
        User user = new User();
        user.setId(1L);
        user.setUsername("ramona");
        user.setEmail("ramona@test.com");
        user.setPassword("123456");

        Mockito.when(userRepository.findByEmailAndPassword("ramona@test.com", "123456"))
                .thenReturn(Optional.of(user));

        var response = userService.login(new UserLoginRequest("ramona@test.com", "123456"));

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.username()).isEqualTo("ramona");
    }

    @Test
    void login_withInvalidCredentials_shouldThrow() {
        Mockito.when(userRepository.findByEmailAndPassword("ramona@test.com", "wrong"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> userService.login(new UserLoginRequest("ramona@test.com", "wrong")));
    }
}

