package com.example.GraduationSystem.controller.web;

import com.example.GraduationSystem.dto.thesisRequest.ThesisRequestDto;
import com.example.GraduationSystem.dto.thesisRequest.ThesisRequestDtoResponse;
import com.example.GraduationSystem.model.Student;
import com.example.GraduationSystem.model.lecturer.Lecturer;
import com.example.GraduationSystem.model.user.UserRole;
import com.example.GraduationSystem.service.LecturerService;
import com.example.GraduationSystem.service.StudentService;
import com.example.GraduationSystem.service.ThesisRequestService;
import com.example.GraduationSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/thesisRequests")
public class WebThesisRequestController {
    private final ThesisRequestService thesisRequestService;
    private final UserService userService;

    public WebThesisRequestController(ThesisRequestService thesisRequestService, UserService userService) {
        this.thesisRequestService = thesisRequestService;
        this.userService = userService;
    }

    @GetMapping
    public String showThesisForm(Model model) {
        Lecturer lecturer = this.findUserTypeByEmail(UserRole.LECTURER);

        if (lecturer == null) {
            throw new RuntimeException("Lecturer not found");
        }

        model.addAttribute("thesisRequest", new ThesisRequestDto());
        model.addAttribute("supervisorId", lecturer.getId());

        return "thesisRequestForm";
    }

    @PostMapping
    public String submitThesisForm(@ModelAttribute("thesisRequest") @Valid ThesisRequestDto thesisRequest, BindingResult result, Model model) {
        Lecturer lecturer = this.findUserTypeByEmail(UserRole.LECTURER);

        if (lecturer == null) {
            throw new RuntimeException("Lecturer not found");
        }

        if(result.hasErrors()) {
            model.addAttribute("supervisorId", lecturer.getId());
            model.addAttribute("thesisRequest", thesisRequest);
            model.addAttribute("errors", result.getFieldErrors());

            return "thesisRequestForm";
        }

        try {
            this.thesisRequestService.createThesisRequest(thesisRequest);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("supervisorId", lecturer.getId());
            model.addAttribute("thesisRequest", thesisRequest);

            return "thesisRequestForm";
        }

        return "redirect:/home";
    }

    @GetMapping("/student")
    public String getStudentThesisRequests(Model model) {
        Student student = this.findUserTypeByEmail(UserRole.STUDENT);

        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        List<ThesisRequestDtoResponse> thesisRequests = thesisRequestService.getStudentThesisRequests(student.getId());
        model.addAttribute("thesisRequests", thesisRequests);

        return "studentThesisRequests";
    }

    private <T> T findUserTypeByEmail(UserRole user) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if(user == UserRole.LECTURER) {
            return (T) this.userService.findLecturerByEmail(email);
        } else {
            return (T) this.userService.findStudentByEmail(email);
        }
    }
}
