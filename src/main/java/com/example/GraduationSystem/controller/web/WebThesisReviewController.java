package com.example.GraduationSystem.controller.web;

import com.example.GraduationSystem.dto.thesis.ThesisDtoResponse;
import com.example.GraduationSystem.dto.thesisReview.ThesisReviewDto;
import com.example.GraduationSystem.dto.thesisReview.UpdateThesisReviewDto;
import com.example.GraduationSystem.model.lecturer.Lecturer;
import com.example.GraduationSystem.service.ThesisReviewService;
import com.example.GraduationSystem.service.ThesisService;
import com.example.GraduationSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/thesisReviews")
public class WebThesisReviewController {
    private final UserService userService;
    private final ThesisService thesisService;
    private final ThesisReviewService thesisReviewService;

    public WebThesisReviewController(UserService userService, ThesisService thesisService, ThesisReviewService thesisReviewService) {
        this.userService = userService;
        this.thesisService = thesisService;
        this.thesisReviewService = thesisReviewService;
    }

    @PostMapping("/assign")
    public String assignReviewer(@RequestParam int thesisId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Lecturer lecturer = userService.findLecturerByEmail(email);

        ThesisReviewDto reviewDto = new ThesisReviewDto();
        reviewDto.setThesisId(thesisId);
        reviewDto.setReviewerId(lecturer.getId());

        this.thesisReviewService.createReview(reviewDto);
        return "redirect:/theses/details/" + thesisId;
    }

    @GetMapping("/upload")
    public String showUploadThesisReviewForm(@RequestParam int thesisId, Model model) {
        return this.showUploadReviewForm(thesisId, new UpdateThesisReviewDto(), model);
    }

    @PostMapping("/upload")
    public String saveReview(@ModelAttribute("thesisReview") @Valid UpdateThesisReviewDto review, @RequestParam int thesisId, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errors", result.getFieldErrors());
            return this.showUploadReviewForm(thesisId, review, model);
        }

        try {
            this.thesisReviewService.updateThesisReview(thesisId, review);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return this.showUploadReviewForm(thesisId, review, model);
        }

        return "redirect:/home";
    }

    private String showUploadReviewForm(int thesisId, UpdateThesisReviewDto review, Model model) {
        ThesisDtoResponse thesis = thesisService.getThesis(thesisId);

        model.addAttribute("thesis", thesis);
        model.addAttribute("thesisReview", review);

        return "uploadThesisReview";
    }
}
