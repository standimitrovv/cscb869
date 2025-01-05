package com.example.GraduationSystem.dto.thesisDefense;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateThesisDefenseGradeDto {
    @DecimalMin(value = "2.0", message = "A valid grade starts from 2.0")
    @DecimalMax(value = "6.0", message = "A valid grade ends at 6.0")
    private double grade;
}
