package com.example.GraduationSystem.dto.session;

import com.example.GraduationSystem.model.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    @Email
    @NotBlank(message = "The 'email' field cannot be blank!")
    private String email;

    @NotBlank(message = "The 'password' field cannot be blank!")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "The 'password' field has to contain one lowercase, one uppercase letter, at least one digit, one special character, and must be at least 8 characters!"
    )
    private String password;

    @NotBlank(message="The 'role' cannot be empty!")
    private UserRole role;
}
