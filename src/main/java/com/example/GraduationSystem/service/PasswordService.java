package com.example.GraduationSystem.service;

public interface PasswordService {
    String encodePassword(String password);

    boolean matches(String rawPassword, String encodedPassword);
}
