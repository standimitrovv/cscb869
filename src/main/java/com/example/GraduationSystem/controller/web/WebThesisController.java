package com.example.GraduationSystem.controller.web;

import com.example.GraduationSystem.dto.thesis.ThesisDto;
import com.example.GraduationSystem.dto.thesis.ThesisDtoResponse;
import com.example.GraduationSystem.dto.thesis.WebThesisDtoResponse;
import com.example.GraduationSystem.dto.thesisReview.ThesisReviewDtoResponse;
import com.example.GraduationSystem.model.Student;
import com.example.GraduationSystem.service.ThesisReviewService;
import com.example.GraduationSystem.service.ThesisService;
import com.example.GraduationSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/theses")
public class WebThesisController {
    private final ThesisService thesisService;
    private final ThesisReviewService thesisReviewService;
    private final UserService userService;

    public WebThesisController(ThesisService thesisService, ThesisReviewService thesisReviewService, UserService userService) {
        this.thesisService = thesisService;
        this.thesisReviewService = thesisReviewService;
        this.userService = userService;
    }

    @GetMapping
    public String getStudentTheses(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = this.userService.findStudentByEmail(email);

        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        List<ThesisDtoResponse> theses = thesisService.getStudentTheses(student.getId());
        model.addAttribute("theses", theses);

        return "studentTheses";
    }

    @GetMapping("/details/{id}")
    public String getStudentThesis(@PathVariable int id, Model model) {
        ThesisDtoResponse thesis = this.thesisService.getThesis(id);

        if (thesis == null) {
            throw new RuntimeException("Thesis not found or you don't have access to it");
        }

        WebThesisDtoResponse webThesisDtoResponse = new WebThesisDtoResponse();
        webThesisDtoResponse.setTitle(thesis.getTitle());
        webThesisDtoResponse.setUploadDate(thesis.getUploadDate());
        webThesisDtoResponse.setId(thesis.getId());
        webThesisDtoResponse.setContent(thesis.getContent());

        Optional<ThesisReviewDtoResponse> review = thesisReviewService.getThesisReviewByThesisId(thesis.getId());
        if(review.isPresent()) {
            ThesisReviewDtoResponse tr = review.get();
            webThesisDtoResponse.setReviewDate(tr.getUploadDate());
            webThesisDtoResponse.setReviewContent(tr.getContent());
            webThesisDtoResponse.setReviewConclusion(tr.getConclusion());
            webThesisDtoResponse.setReviewer(tr.getReviewer().getName());
        }

        model.addAttribute("thesis", webThesisDtoResponse);
        return "thesisDetails";
    }

    @GetMapping("/upload")
    public String showUploadThesisForm(@RequestParam int requestId, Model model) {
        model.addAttribute("requestId", requestId);
        model.addAttribute("thesisDto", new ThesisDto());

        return "uploadThesis";
    }

    @PostMapping("/upload")
    public String uploadThesis(@RequestParam int requestId,
                               @ModelAttribute("thesisDto") @Valid ThesisDto thesisDto,
                               Model model) {
        try {
            thesisService.createThesis(requestId, thesisDto);
            return "redirect:/theses";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "uploadThesis";
        }
    }
}
