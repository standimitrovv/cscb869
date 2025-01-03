package com.example.GraduationSystem.dto.student;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentDto {
    @NotBlank(message = "The 'name' field cannot be blank!")
    @Size(min = 2, max = 25, message = "The 'name' field has to contain at least 2 and at most 25 characters!")
    private String name;

    @NotBlank(message = "The 'facultyNumber' field cannot be blank!")
    @Size(min = 7, max = 25, message = "The 'facultyNumber' field has to contain at least 7 and at most 25 characters!")
    private String facultyNumber;
}
