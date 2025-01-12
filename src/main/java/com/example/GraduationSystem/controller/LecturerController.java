package com.example.GraduationSystem.controller;

import com.example.GraduationSystem.dto.lecturer.LecturerDtoResponse;
import com.example.GraduationSystem.dto.lecturer.UpdateLecturerPositionDto;
import com.example.GraduationSystem.service.implementation.LecturerServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lecturers")
public class LecturerController {
    private final LecturerServiceImpl lecturerServiceImpl;

    @Autowired
    LecturerController(LecturerServiceImpl lecturerServiceImpl) {
        this.lecturerServiceImpl = lecturerServiceImpl;
    }

    @GetMapping
    public List<LecturerDtoResponse> getLecturers() {
        return this.lecturerServiceImpl.getLecturers();
    }

    @GetMapping("/{lecturerId}")
    public LecturerDtoResponse getLecturer(@PathVariable int lecturerId){
        return this.lecturerServiceImpl.getLecturer(lecturerId);
    }

    @PatchMapping("/{lecturerId}")
    public LecturerDtoResponse updateLecturerPosition(@PathVariable int lecturerId,
                                            @RequestBody @Valid UpdateLecturerPositionDto dto) {
        return this.lecturerServiceImpl.updateLecturerPosition(lecturerId, dto);
    }

    @DeleteMapping("/{lecturerId}")
    public ResponseEntity<String> deleteLecturer(@PathVariable int lecturerId){
        this.lecturerServiceImpl.deleteLecturer(lecturerId);
        return ResponseEntity.ok("The lecturer was successfully deleted!");
    }
}
