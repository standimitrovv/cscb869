package com.example.GraduationSystem.exception.lecturer;

public class LecturerNotFoundException extends RuntimeException {
    public LecturerNotFoundException() {
        super("There's no Lecturer with the provided ID!");
    }
}
