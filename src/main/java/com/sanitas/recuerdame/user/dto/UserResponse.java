package com.sanitas.recuerdame.user.dto;

public record UserResponse(
        Long id,
        String username,
        String email
) {}