package com.example.GraduationSystem.controller;

import com.example.GraduationSystem.dto.student.StudentDto;
import com.example.GraduationSystem.dto.student.StudentDtoResponse;
import com.example.GraduationSystem.service.implementation.StudentServiceImpl;
import jakarta.validation.Valid;
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

    @PostMapping
    public StudentDtoResponse createStudent(@RequestBody @Valid StudentDto student){
        return this.studentServiceImpl.createStudent(student);
    }

    @GetMapping
    public List<StudentDtoResponse> getStudents() {
        return this.studentServiceImpl.getStudents();
    }

    @GetMapping("/{studentId}")
    public StudentDtoResponse getStudent(@PathVariable int studentId){
        return this.studentServiceImpl.getStudent(studentId);
    }

    @PatchMapping("/{studentId}")
    public StudentDtoResponse updateStudent(@PathVariable int studentId,
                                          @RequestBody @Valid StudentDto studentDto) {
        return this.studentServiceImpl.updateStudent(studentId, studentDto);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable int studentId){
        this.studentServiceImpl.deleteStudent(studentId);
        return ResponseEntity.ok("The student was successfully deleted!");
    }
}
