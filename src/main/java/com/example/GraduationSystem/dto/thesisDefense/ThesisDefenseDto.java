package com.example.GraduationSystem.dto.thesisDefense;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThesisDefenseDto {
    @NotEmpty(message = "No studentIds provided")
    private List<Integer> studentIds;

    @NotEmpty(message = "No lecturerIds provided")
    private List<Integer> lecturerIds;
}
