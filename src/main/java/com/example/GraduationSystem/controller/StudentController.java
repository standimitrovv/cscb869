package com.example.GraduationSystem.controller;

import com.example.GraduationSystem.dto.student.StudentDtoResponse;
import com.example.GraduationSystem.service.implementation.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentServiceImpl studentServiceImpl;

    @Autowired
    public StudentController(StudentServiceImpl studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }

    @GetMapping
    public List<StudentDtoResponse> getStudents() {
        return this.studentServiceImpl.getStudents();
    }

    @GetMapping("/{studentId}")
    public StudentDtoResponse getStudent(@PathVariable int studentId){
        return this.studentServiceImpl.getStudent(studentId);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable int studentId){
        this.studentServiceImpl.deleteStudent(studentId);
        return ResponseEntity.ok("The student was successfully deleted!");
    }
}
