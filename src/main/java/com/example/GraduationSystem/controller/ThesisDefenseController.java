package com.example.GraduationSystem.controller;

import com.example.GraduationSystem.dto.student.StudentDtoResponse;
import com.example.GraduationSystem.dto.thesisDefense.ThesisDefenseDto;
import com.example.GraduationSystem.dto.thesisDefense.ThesisDefenseDtoResponse;
import com.example.GraduationSystem.dto.thesisDefense.UpdateThesisDefenseGradeDto;
import com.example.GraduationSystem.service.implementation.ThesisDefenseServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/thesisDefenses")
public class ThesisDefenseController {
    private final ThesisDefenseServiceImpl thesisDefenseServiceImpl;

    @Autowired
    public ThesisDefenseController(ThesisDefenseServiceImpl thesisDefenseServiceImpl) {
        this.thesisDefenseServiceImpl = thesisDefenseServiceImpl;
    }

    @PostMapping
    public ThesisDefenseDtoResponse createDefense(@RequestBody @Valid ThesisDefenseDto thesisDefenseDto) {
        return this.thesisDefenseServiceImpl.createDefense(thesisDefenseDto);
    }

    @PatchMapping("/{defenseId}/students/{studentId}")
    public ThesisDefenseDtoResponse addStudent(@PathVariable int defenseId, @PathVariable int studentId) {
        return this.thesisDefenseServiceImpl.addStudent(defenseId, studentId);
    }

    @PatchMapping("/{defenseId}/lecturers/{lecturerId}")
    public ThesisDefenseDtoResponse addLecturer(@PathVariable int defenseId, @PathVariable int lecturerId) {
        return this.thesisDefenseServiceImpl.addLecturer(defenseId, lecturerId);
    }

    @PatchMapping("/{defenseId}/students/{studentId}/grade")
    public ThesisDefenseDtoResponse assignStudentGrade(
            @PathVariable int defenseId,
            @PathVariable int studentId,
            @RequestBody @Valid UpdateThesisDefenseGradeDto thesisDefenseGradeDto
    ) {
        return this.thesisDefenseServiceImpl.assignStudentGrade(defenseId, studentId, thesisDefenseGradeDto);
    }

    @GetMapping("/students")
    public List<StudentDtoResponse> getStudentsAppearedInDefensesBetween(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return this.thesisDefenseServiceImpl.getStudentsInDefensePeriod(startDate, endDate);
    }
}
