package com.example.GraduationSystem.service.implementation;

import com.example.GraduationSystem.service.PasswordService;
import com.example.GraduationSystem.utils.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {
    private final PasswordEncoderUtil passwordEncoderUtil;

    @Autowired
    public PasswordServiceImpl(PasswordEncoderUtil passwordEncoderUtil) {
        this.passwordEncoderUtil = passwordEncoderUtil;
    }

    @Override
    public String encodePassword(String password) {
        return this.passwordEncoderUtil.passwordEncoder().encode(password);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return this.passwordEncoderUtil.passwordEncoder().matches(rawPassword, encodedPassword);
    }
}
