package com.seven.schoolapi.dtos;

import com.seven.schoolapi.validation.EmailValidation;

public record UserDTO(Long id, String name, String username, @EmailValidation String email, String password, String role) {}