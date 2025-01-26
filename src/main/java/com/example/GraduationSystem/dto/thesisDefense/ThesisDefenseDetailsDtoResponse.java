package com.example.GraduationSystem.dto.thesisDefense;

import com.example.GraduationSystem.model.thesisDefense.ThesisDefenseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThesisDefenseDetailsDtoResponse {
    private int id;

    private LocalDate date;

    private ThesisDefenseStatus status;

    private double grade;
}
