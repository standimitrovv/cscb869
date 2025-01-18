package com.example.GraduationSystem.controller.web;

import com.example.GraduationSystem.dto.thesisRequest.ThesisRequestDto;
import com.example.GraduationSystem.dto.thesisRequest.ThesisRequestDtoResponse;
import com.example.GraduationSystem.model.Student;
import com.example.GraduationSystem.model.lecturer.Lecturer;
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
    private final StudentService studentService;
    private final LecturerService lecturerService;

    public WebThesisRequestController(ThesisRequestService thesisRequestService, UserService userService, StudentService studentService, LecturerService lecturerService) {
        this.thesisRequestService = thesisRequestService;
        this.userService = userService;
        this.studentService = studentService;
        this.lecturerService = lecturerService;
    }

    @GetMapping
    public String showThesisForm(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Lecturer lecturer = userService.findLecturerByEmail(email);

        if (lecturer == null) {
            throw new RuntimeException("Lecturer not found");
        }

        model.addAttribute("thesisRequest", new ThesisRequestDto());
        model.addAttribute("supervisorId", lecturer.getId());

        return "thesisRequestForm";
    }

    @PostMapping
    public String submitThesisForm(@ModelAttribute("thesisRequest") @Valid ThesisRequestDto thesisRequest, BindingResult result, Model model) {
        try {
            this.thesisRequestService.createThesisRequest(thesisRequest);
            return "redirect:/home";
        } catch(Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "thesisRequestForm";
        }
    }

    @GetMapping("/student")
    public String getStudentThesisRequests(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = this.userService.findStudentByEmail(email);

        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        List<ThesisRequestDtoResponse> thesisRequests = thesisRequestService.getStudentThesisRequests(student.getId());
        model.addAttribute("thesisRequests", thesisRequests);

        return "studentThesisRequests";
    }

}
