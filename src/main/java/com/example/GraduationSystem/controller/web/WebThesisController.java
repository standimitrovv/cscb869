package com.example.GraduationSystem.controller.web;

import com.example.GraduationSystem.dto.thesis.ThesisDto;
import com.example.GraduationSystem.service.ThesisService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/theses")
public class WebThesisController {
    private final ThesisService thesisService;

    public WebThesisController(ThesisService thesisService) {
        this.thesisService = thesisService;
    }

    @GetMapping
    public String showUploadThesisForm(@RequestParam int requestId, Model model) {
        model.addAttribute("requestId", requestId);
        model.addAttribute("thesisDto", new ThesisDto());

        return "uploadThesis";
    }

    @PostMapping
    public String uploadThesis(@RequestParam int requestId,
                               @ModelAttribute("thesisDto") @Valid ThesisDto thesisDto,
                               Model model) {
        try {
            thesisService.createThesis(requestId, thesisDto);
            return "redirect:/home";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "uploadThesis";
        }
    }
}
