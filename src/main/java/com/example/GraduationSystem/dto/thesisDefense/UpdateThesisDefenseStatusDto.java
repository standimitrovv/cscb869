package com.example.GraduationSystem.dto.thesisDefense;

import com.example.GraduationSystem.model.thesisDefense.ThesisDefenseStatus;
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
public class UpdateThesisDefenseStatusDto {
    @NotNull(message = "The 'status' field cannot be null!")
    @Enumerated(EnumType.STRING)
    private ThesisDefenseStatus status;
}
