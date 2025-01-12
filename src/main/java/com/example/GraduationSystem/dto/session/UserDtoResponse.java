package com.example.GraduationSystem.dto.session;

import com.example.GraduationSystem.model.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDtoResponse {
    private int id;

    private String email;

    private UserRole role;
}
