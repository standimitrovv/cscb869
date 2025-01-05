package com.example.GraduationSystem.dto.thesisReview;

import com.example.GraduationSystem.model.thesisReview.ThesisReviewConclusion;
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
public class UpdateThesisReviewConclusionDto {
    @NotNull
    @Enumerated(EnumType.STRING)
    private ThesisReviewConclusion conclusion;
}
