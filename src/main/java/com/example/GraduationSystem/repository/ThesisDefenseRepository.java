package com.example.GraduationSystem.repository;

import com.example.GraduationSystem.model.Student;
import com.example.GraduationSystem.model.thesisDefense.ThesisDefense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ThesisDefenseRepository extends JpaRepository<ThesisDefense, Integer> {
    @Query("""
        SELECT DISTINCT s
        FROM ThesisDefense td
        JOIN td.grades tdg
        JOIN tdg.student s
        WHERE td.date BETWEEN :startDate AND :endDate
    """)
    List<Student> findStudentsAppearedInDefensesBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
