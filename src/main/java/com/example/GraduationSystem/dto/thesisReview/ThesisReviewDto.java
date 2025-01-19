package com.example.GraduationSystem.dto.thesisReview;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThesisReviewDto {
    @NotNull(message = "The thesisId is required!")
    private int thesisId;

    @NotNull(message = "The reviewerId is required!")
    private int reviewerId;
}
