package com.example.GraduationSystem.service;

import com.example.GraduationSystem.dto.thesisReview.ThesisReviewDto;
import com.example.GraduationSystem.dto.thesisReview.ThesisReviewDtoResponse;
import com.example.GraduationSystem.dto.thesisReview.UpdateThesisReviewConclusionDto;
import com.example.GraduationSystem.model.Student;

import java.util.List;

public interface ThesisReviewService {
    ThesisReviewDtoResponse createReview(ThesisReviewDto thesisReviewDto);

    void updateThesisReviewConclusion(int thesisReviewId, UpdateThesisReviewConclusionDto thesisReviewConclusionDto);

    List<Student> getStudentsWithNegativeReviews();
}
