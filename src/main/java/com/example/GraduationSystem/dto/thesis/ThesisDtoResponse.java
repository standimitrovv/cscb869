package com.example.GraduationSystem.dto.thesis;

import com.example.GraduationSystem.dto.thesisRequest.ThesisRequestDtoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThesisDtoResponse {
    private int id;

    private String title;

    private String content;

    private LocalDate uploadDate;

    private ThesisRequestDtoResponse thesisRequest;
}
