package com.example.GraduationSystem.repository;

import com.example.GraduationSystem.model.lecturer.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {
}
