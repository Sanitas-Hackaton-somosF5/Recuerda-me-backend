
package com.sanitas.recuerdame.user.serviceImplTest;

import com.sanitas.recuerdame.user.dto.UserRequest;
import com.sanitas.recuerdame.user.dto.UserResponse;
import com.sanitas.recuerdame.user.entity.User;
import com.sanitas.recuerdame.user.mapper.UserMapper;
import com.sanitas.recuerdame.user.repository.UserRepository;
import com.sanitas.recuerdame.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        userService = new UserServiceImpl(userRepository, userMapper);
    }


    private User buildUser(Long id, String username) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        return user;
    }

    private UserResponse buildUserResponse(Long id, String username) {
        UserResponse dto = new UserResponse();
        dto.setId(id);
        dto.setUsername(username);
        return dto;
    }

    @Test
    void createUser_shouldReturnDTO() {
        UserRequest request = new UserRequest();
        request.setUsername("pico");
        request.setPassword("1234");

        User entity = buildUser(null, "pico");
        User saved = buildUser(1L, "pico");
        UserResponse dto = buildUserResponse(1L, "pico");

        when(userMapper.toEntity(request)).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(saved);
        when(userMapper.toDTO(saved)).thenReturn(dto);

        UserResponse result = userService.createUser(request);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUsername()).isEqualTo("pico");
        verify(userRepository, times(1)).save(entity);
    }

    @Test
    void getAllUsers_shouldReturnDTOList() {
        User u1 = buildUser(1L, "pico");
        User u2 = buildUser(2L, "maria");

        when(userRepository.findAll()).thenReturn(List.of(u1, u2));
        when(userMapper.toDTO(u1)).thenReturn(buildUserResponse(1L, "pico"));
        when(userMapper.toDTO(u2)).thenReturn(buildUserResponse(2L, "maria"));

        List<UserResponse> result = userService.getAllUsers();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUsername()).isEqualTo("pico");
        assertThat(result.get(1).getUsername()).isEqualTo("maria");
    }

    @Test
    void getUserById_existing_shouldReturnDTO() {
        User user = buildUser(1L, "alex");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(buildUserResponse(1L, "pico"));

        UserResponse result = userService.getUserById(1L);

        assertThat(result.getUsername()).isEqualTo("pico");
    }

    @Test
    void getUserById_nonExisting_shouldThrow() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = null;
        try {
            userService.getUserById(1L);
        } catch (RuntimeException e) {
            exception = e;
        }
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("User not found");
    }

    @Test
    void deleteUser_shouldCallRepository() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}