package com.example.GraduationSystem.exception.student;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException() {
        super("There's no Student with the provided ID!");
    }
}
