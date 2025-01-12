package com.example.GraduationSystem.dto.session;

import com.example.GraduationSystem.model.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationRequestDto {
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

    @NotBlank(message="The 'firstName' field cannot be empty!")
    @Size(min = 2, max = 12, message = "The 'firstName' field can contain at least 2 characters and at most 12")
    private String firstName;

    @NotBlank(message="The 'lastName' field cannot be empty!")
    @Size(min = 2, max = 20, message = "The 'lastName' field can contain at least 2 characters and at most 20")
    private String lastName;
}
