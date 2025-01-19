package com.example.GraduationSystem.dto.thesisReview;

import com.example.GraduationSystem.model.thesisReview.ThesisReviewConclusion;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateThesisReviewDto {
    @NotBlank(message = "The content of the thesis cannot be blank!")
    @Size(min = 20, max = 100, message = "The content must be between 20 and 100 characters!")
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ThesisReviewConclusion conclusion;
}
