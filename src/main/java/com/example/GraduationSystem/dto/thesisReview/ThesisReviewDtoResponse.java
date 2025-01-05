package com.example.GraduationSystem.dto.thesisReview;


import com.example.GraduationSystem.dto.lecturer.LecturerDtoResponse;
import com.example.GraduationSystem.dto.thesis.ThesisDtoResponse;
import com.example.GraduationSystem.model.thesisReview.ThesisReviewConclusion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThesisReviewDtoResponse {
    private int id;

    private String content;

    private ThesisReviewConclusion conclusion;

    private ThesisDtoResponse thesis;

    private LecturerDtoResponse reviewer;
}
