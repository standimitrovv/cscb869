package com.example.GraduationSystem.repository;

import com.example.GraduationSystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("SELECT s FROM Student s WHERE s.facultyNumber = :facultyNumber")
    Optional<Student> findByFacultyNumber(@Param("facultyNumber") String facultyNumber);
}
