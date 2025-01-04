package com.example.GraduationSystem.dto.thesisRequest;

import com.example.GraduationSystem.dto.lecturer.LecturerDtoResponse;
import com.example.GraduationSystem.dto.student.StudentDtoResponse;
import com.example.GraduationSystem.model.thesisRequest.ThesisRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThesisRequestDtoResponse {
    private int id;

    private String topic;

    private String goal;

    private String tasks;

    private String technologies;

    private LocalDate submissionDate;

    private StudentDtoResponse student;

    private LecturerDtoResponse supervisor;

    private ThesisRequestStatus status;
}
