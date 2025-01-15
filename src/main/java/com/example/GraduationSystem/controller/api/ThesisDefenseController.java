package com.example.GraduationSystem.controller.api;

import com.example.GraduationSystem.dto.student.StudentDtoResponse;
import com.example.GraduationSystem.dto.thesisDefense.ThesisDefenseDto;
import com.example.GraduationSystem.dto.thesisDefense.ThesisDefenseDtoResponse;
import com.example.GraduationSystem.dto.thesisDefense.UpdateThesisDefenseGradeDto;
import com.example.GraduationSystem.dto.thesisDefense.UpdateThesisDefenseStatusDto;
import com.example.GraduationSystem.service.implementation.ThesisDefenseServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/thesisDefenses")
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

    @GetMapping
    public List<ThesisDefenseDtoResponse> getDefenses() {
        return this.thesisDefenseServiceImpl.getDefenses();
    }

    @GetMapping("/{defenseId}")
    public ThesisDefenseDtoResponse getDefense(@PathVariable int defenseId) {
        return this.thesisDefenseServiceImpl.getDefense(defenseId);
    }

    @GetMapping("/students")
    public List<StudentDtoResponse> getStudentsAppearedInDefensesBetween(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return this.thesisDefenseServiceImpl.getStudentsInDefensePeriod(startDate, endDate);
    }

    @GetMapping("/students/graduates")
    public List<StudentDtoResponse> getGraduatedStudentsInPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return this.thesisDefenseServiceImpl.getGraduatedStudentsInPeriod(startDate, endDate);
    }

    @GetMapping("/lecturers/successful")
    public ResponseEntity<Long> getSuccessfulDefensesByLecturer(@RequestParam int lecturerId) {
        long successfulDefenses = this.thesisDefenseServiceImpl.getSuccessfulDefensesByLecturer(lecturerId);
        return ResponseEntity.ok(successfulDefenses);
    }

    @PatchMapping("/{defenseId}/status")
    public void updateThesisDefenseStatus(@PathVariable int defenseId, @RequestBody @Valid UpdateThesisDefenseStatusDto status) {
        this.thesisDefenseServiceImpl.updateDefenseStatus(defenseId, status);
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

    @DeleteMapping("/{defenseId}")
    public void cancelDefense(@PathVariable int defenseId){
        this.thesisDefenseServiceImpl.cancelDefense(defenseId);
    }
}
