package com.example.GraduationSystem.controller;

import com.example.GraduationSystem.dto.thesisReview.ThesisReviewDto;
import com.example.GraduationSystem.dto.thesisReview.ThesisReviewDtoResponse;
import com.example.GraduationSystem.dto.thesisReview.UpdateThesisReviewConclusionDto;
import com.example.GraduationSystem.service.implementation.ThesisReviewServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/thesisReviews")
public class ThesisReviewController {
    private final ThesisReviewServiceImpl thesisReviewServiceImpl;

    @Autowired
    public ThesisReviewController(ThesisReviewServiceImpl thesisReviewServiceImpl) {
        this.thesisReviewServiceImpl = thesisReviewServiceImpl;
    }

    @PostMapping
    public ThesisReviewDtoResponse createThesisReview(@RequestBody @Valid ThesisReviewDto thesisReviewDto){
        return this.thesisReviewServiceImpl.createReview(thesisReviewDto);
    }

    @PatchMapping("/{thesisReviewId}")
    public void updateThesisReviewConclusion(@PathVariable int thesisReviewId, @RequestBody @Valid UpdateThesisReviewConclusionDto thesisReviewConclusionDto) {
        this.thesisReviewServiceImpl.updateThesisReviewConclusion(thesisReviewId, thesisReviewConclusionDto);
    }
}
