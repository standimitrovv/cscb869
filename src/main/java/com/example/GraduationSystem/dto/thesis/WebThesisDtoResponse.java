package com.example.GraduationSystem.dto.thesis;

import com.example.GraduationSystem.model.thesisDefense.ThesisDefenseStatus;
import com.example.GraduationSystem.model.thesisReview.ThesisReviewConclusion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WebThesisDtoResponse {
    private int id;

    private String title;

    private String content;

    private LocalDate uploadDate;

    private LocalDate reviewDate;

    private String reviewContent;

    private ThesisReviewConclusion reviewConclusion;

    private String reviewer;

    private LocalDate defenseDate;

    private ThesisDefenseStatus defenseStatus;

    private double defenseGrade;
}