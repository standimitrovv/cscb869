package com.example.GraduationSystem.service;

import com.example.GraduationSystem.dto.thesisReview.ThesisReviewDto;
import com.example.GraduationSystem.dto.thesisReview.ThesisReviewDtoResponse;
import com.example.GraduationSystem.dto.thesisReview.UpdateThesisReviewConclusionDto;

public interface ThesisReviewService {
    ThesisReviewDtoResponse createReview(ThesisReviewDto thesisReviewDto);

    void updateThesisReviewConclusion(int thesisReviewId, UpdateThesisReviewConclusionDto thesisReviewConclusionDto);
}
