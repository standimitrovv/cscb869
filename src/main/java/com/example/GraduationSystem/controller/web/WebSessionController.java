package com.example.GraduationSystem.controller.web;

import com.example.GraduationSystem.auth.Jwt;
import com.example.GraduationSystem.dto.session.LoginRequestDto;
import com.example.GraduationSystem.dto.session.RegistrationRequestDto;
import com.example.GraduationSystem.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/session")
public class WebSessionController {
    private final SessionService sessionService;
    private final AuthenticationManager authenticationManager;
    private final Jwt jwtUtil;

    @Autowired
    public WebSessionController(SessionService sessionService, AuthenticationManager authenticationManager, Jwt jwtUtil) {
        this.sessionService = sessionService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequestDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("registrationRequest") @Valid RegistrationRequestDto requestDto,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        try {
            sessionService.registerUser(requestDto);
            return "redirect:/session/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Registration failed: " + e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequestDto());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginRequest") @Valid LoginRequestDto loginRequest,
                        BindingResult result,
                        HttpServletResponse response,
                        Model model) {
        if (result.hasErrors()) {
            return "login";
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            String token = jwtUtil.generateToken(authentication.getName());

            Cookie jwtCookie = new Cookie("JWT_TOKEN", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setMaxAge(10 * 60 * 60); // 10 hours
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);

            return "redirect:/home";
        } catch (AuthenticationException e) {
            model.addAttribute("errorMessage", "Invalid username or password.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("JWT_TOKEN", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge(0);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);

        SecurityContextHolder.clearContext();
        return "redirect:/session/login";
    }
}
