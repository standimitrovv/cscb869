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

    @Query("""
        SELECT DISTINCT s
        FROM ThesisDefense td
        JOIN td.students s
        JOIN td.grades g
        WHERE td.date BETWEEN :startDate AND :endDate
        AND g.grade >= 3
    """)
    List<Student> findGraduatedStudentsInPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("""
        SELECT CASE WHEN COUNT(dl) > 0 THEN TRUE ELSE FALSE END
        FROM ThesisDefense td
        JOIN td.lecturers dl
        WHERE dl.id = :lecturerId
    """)
    boolean isLecturerAssignedToAnyDefense(@Param("lecturerId") int lecturerId);

    @Query("""
        SELECT COUNT(DISTINCT tdg.student.id)
        FROM ThesisDefense td
        JOIN td.grades tdg
        JOIN td.lecturers l
        WHERE l.id = :lecturerId AND tdg.grade >= 3.0
    """)
    long countSuccessfulDefensesByLecturer(@Param("lecturerId") int lecturerId);

    @Query(value = """
        SELECT td.id, td.date, td.status, tdg.grade
        FROM thesis_defenses AS td
        JOIN theses AS t
        ON t.defense_id = td.id
        JOIN thesis_defense_grades AS tdg
        ON tdg.defense_id = td.id
        WHERE t.id = :thesisId
    """, nativeQuery = true)
    List<Object[]> findThesisDefensesByThesisId(@Param("thesisId") int thesisId);
}
