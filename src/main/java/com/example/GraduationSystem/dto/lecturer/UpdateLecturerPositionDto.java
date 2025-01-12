package com.example.GraduationSystem.dto.lecturer;

import com.example.GraduationSystem.model.lecturer.LecturerPosition;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateLecturerPositionDto {
    @NotNull(message = "The 'position' field cannot be null!")
    @Enumerated(EnumType.STRING)
    private LecturerPosition position;
}
