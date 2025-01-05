package com.example.GraduationSystem.dto.thesisReview;

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
public class ThesisReviewDto {
    @NotBlank(message = "The content of the thesis cannot be blank!")
    @Size(min = 20, max = 100, message = "The content must be between 20 and 100 characters!")
    private String content;

    @NotNull(message = "The thesisId is required!")
    private int thesisId;

    @NotNull(message = "The reviewerId is required!")
    private int reviewerId;
}
