package com.example.GraduationSystem.dto.thesisDefense;

import com.example.GraduationSystem.dto.lecturer.LecturerDtoResponse;
import com.example.GraduationSystem.dto.student.StudentDtoResponse;
import com.example.GraduationSystem.dto.thesis.ThesisDtoResponse;
import com.example.GraduationSystem.model.thesisDefense.ThesisDefenseGrade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThesisDefenseDtoResponse {
    private int id;

    private LocalDate date;

    private List<StudentDtoResponse> students;

    private List<LecturerDtoResponse> lecturers;

    private List<ThesisDefenseGrade> grades;

    private List<ThesisDtoResponse> thesis;
}
