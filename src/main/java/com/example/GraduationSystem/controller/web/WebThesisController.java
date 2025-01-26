package com.example.GraduationSystem.controller.web;

import com.example.GraduationSystem.dto.thesis.ThesisDto;
import com.example.GraduationSystem.dto.thesis.ThesisDtoResponse;
import com.example.GraduationSystem.dto.thesis.WebThesisDtoResponse;
import com.example.GraduationSystem.dto.thesisDefense.ThesisDefenseDetailsDtoResponse;
import com.example.GraduationSystem.dto.thesisReview.ThesisReviewDtoResponse;
import com.example.GraduationSystem.model.Student;
import com.example.GraduationSystem.model.lecturer.Lecturer;
import com.example.GraduationSystem.service.ThesisDefenseService;
import com.example.GraduationSystem.service.ThesisReviewService;
import com.example.GraduationSystem.service.ThesisService;
import com.example.GraduationSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/theses")
public class WebThesisController {
    private final ThesisService thesisService;
    private final ThesisReviewService thesisReviewService;
    private final ThesisDefenseService thesisDefenseService;
    private final UserService userService;

    public WebThesisController(ThesisService thesisService, ThesisReviewService thesisReviewService, ThesisDefenseService thesisDefenseService, UserService userService) {
        this.thesisService = thesisService;
        this.thesisReviewService = thesisReviewService;
        this.thesisDefenseService = thesisDefenseService;
        this.userService = userService;
    }

    @GetMapping
    public String getStudentTheses(Model model) {
        Student student = this.getStudent();

        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        List<ThesisDtoResponse> theses = thesisService.getStudentTheses(student.getId());
        model.addAttribute("theses", theses);
        model.addAttribute("userRole", this.getUserRole());

        return "theses";
    }

    @GetMapping("/details/{id}")
    public String getThesisDetails(@PathVariable int id, Model model) {
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

        Optional<ThesisDefenseDetailsDtoResponse> defense = this.thesisDefenseService.getThesisDefenseDetails(thesis.getId());
        if(defense.isPresent()) {
            ThesisDefenseDetailsDtoResponse defenseDetails = defense.get();
            webThesisDtoResponse.setDefenseDate(defenseDetails.getDate());
            webThesisDtoResponse.setDefenseStatus(defenseDetails.getStatus());
            webThesisDtoResponse.setDefenseGrade(defenseDetails.getGrade());
        }

        model.addAttribute("thesis", webThesisDtoResponse);

        String userRole = this.getUserRole();
        if(Objects.equals(userRole, "ROLE_LECTURER")) {
            boolean userIsReviewer = review.isPresent() && review.get().getReviewer().getId() == this.getLecturer().getId();
            model.addAttribute("isReviewer", userIsReviewer);
        }

        model.addAttribute("userRole", userRole);


        return "thesisDetails";
    }

    @GetMapping("/upload")
    public String showUploadThesisForm(@RequestParam int requestId, Model model) {
        return this.showUploadForm(requestId, new ThesisDto(), model);
    }

    @PostMapping("/upload")
    public String uploadThesis(@RequestParam int requestId,
                               @ModelAttribute("thesisDto") @Valid ThesisDto thesisDto,
                               BindingResult result,
                               Model model) {
        if(result.hasErrors()) {
            model.addAttribute("errors", result.getFieldErrors());
            return this.showUploadForm(requestId, thesisDto, model);
        }

        try {
            thesisService.createThesis(requestId, thesisDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return this.showUploadForm(requestId, thesisDto, model);
        }

        return "redirect:/theses";
    }

    @GetMapping("/unreviewed")
    public String showThesesWaitingForReview(Model model) {
        List<ThesisDtoResponse> theses = this.thesisService.getThesesWaitingForReview();

        model.addAttribute("theses", theses);
        model.addAttribute("userRole", this.getUserRole());

        return "theses";
    }

    @GetMapping("/lecturer/pending")
    public String showPendingLecturerTheses(Model model) {
        List<ThesisDtoResponse> theses = this.thesisService.getPendingLecturerTheses(this.getLecturer().getId());

        model.addAttribute("theses", theses);
        model.addAttribute("userRole", this.getUserRole());
        model.addAttribute("isReviewer", true);

        return "theses";
    }

    private String showUploadForm(int requestId, ThesisDto thesis, Model model) {
        model.addAttribute("requestId", requestId);
        model.addAttribute("thesisDto", thesis);

        return "uploadThesis";
    }

    private String getUserRole() {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().iterator().next().getAuthority();
    }

    private Lecturer getLecturer() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.userService.findLecturerByEmail(email);
    }

    private Student getStudent() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.userService.findStudentByEmail(email);
    }
}
