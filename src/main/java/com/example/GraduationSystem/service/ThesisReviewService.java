package com.example.GraduationSystem.service;

import com.example.GraduationSystem.dto.thesisReview.ThesisReviewDto;
import com.example.GraduationSystem.dto.thesisReview.ThesisReviewDtoResponse;
import com.example.GraduationSystem.dto.thesisReview.UpdateThesisReviewConclusionDto;
import com.example.GraduationSystem.dto.thesisReview.UpdateThesisReviewDto;
import com.example.GraduationSystem.model.Student;

import java.util.List;
import java.util.Optional;

public interface ThesisReviewService {
    ThesisReviewDtoResponse createReview(ThesisReviewDto thesisReviewDto);

    void updateThesisReview(int thesisReviewId, UpdateThesisReviewDto dto);

    void updateThesisReviewConclusion(int thesisReviewId, UpdateThesisReviewConclusionDto thesisReviewConclusionDto);

    List<Student> getStudentsWithNegativeReviews();

    Optional<ThesisReviewDtoResponse> getThesisReviewByThesisId(int thesisId);
}
