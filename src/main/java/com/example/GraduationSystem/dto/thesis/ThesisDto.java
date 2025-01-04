package com.example.GraduationSystem.dto.thesis;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThesisDto {
    @NotBlank(message = "The 'title' field cannot be blank!")
    @Size(min = 5, max = 100, message = "The 'title' field must contain between 5 and 100 characters!")
    private String title;

    @NotBlank(message = "The 'content' field cannot be blank!")
    @Size(min = 50, message = "The 'content' field must contain at least 50 characters!")
    private String content;
}
