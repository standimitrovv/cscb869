package com.example.GraduationSystem.dto.lecturer;

import com.example.GraduationSystem.model.lecturer.LecturerPosition;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LecturerDto {
    @NotBlank(message = "The 'name' field cannot be blank!")
    @Size(min = 2, max = 25, message = "The 'name' field has to contain at least 2 and at most 25 characters!")
    private String name;

    @NotNull(message = "The 'position' field cannot be null!")
    @Enumerated(EnumType.STRING)
    private LecturerPosition position;
}
