package com.seven.schoolapi.dtos;

public record PasswordResetDTO(String token, String newPassword) { }