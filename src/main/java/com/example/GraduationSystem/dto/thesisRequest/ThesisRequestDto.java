package com.example.GraduationSystem.dto.thesisRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThesisRequestDto {
    @NotBlank(message = "The topic of the thesis cannot be blank!")
    @Size(min = 5, max = 100, message = "The topic must be between 5 and 100 characters!")
    private String topic;

    @NotBlank(message = "The goal of the thesis cannot be blank!")
    @Size(min = 10, message = "The goal must be at least 10 characters long!")
    private String goal;

    @NotBlank(message = "The tasks of the thesis cannot be blank!")
    @Size(min = 10, message = "The tasks must be at least 10 characters long!")
    private String tasks;

    @NotBlank(message = "The technologies to complete the thesis cannot be blank!")
    @Size(min = 5, message = "The technologies must be at least 5 characters long!")
    private String technologies;

    @NotNull(message = "Student ID is required!")
    private int studentId;

    @NotNull(message = "Supervisor ID is required!")
    private int supervisorId;
}
