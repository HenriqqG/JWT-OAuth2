package com.org.jwtoauth.model.dto;

public record RegisterDTO(
        String cpf,
        String password,
        String role
) {}
