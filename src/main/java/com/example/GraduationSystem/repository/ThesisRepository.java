package com.example.GraduationSystem.repository;

import com.example.GraduationSystem.model.Thesis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThesisRepository extends JpaRepository<Thesis, Integer> {
    List<Thesis> findByTitleContainingIgnoreCase(String keyword);

    @Query(value = """
    SELECT t.* 
    FROM theses t
    JOIN thesis_defenses d ON t.defense_id = d.id
    JOIN thesis_defense_grades g ON d.id = g.defense_id
    WHERE g.grade BETWEEN :minGrade AND :maxGrade
""", nativeQuery = true)
    List<Thesis> findAllByGradeRange(@Param("minGrade") double minGrade, @Param("maxGrade") double maxGrade);

    @Query("""
        SELECT t
        FROM Thesis t
        JOIN t.thesisRequest tr
        JOIN tr.student s
        WHERE s.id = :studentId
    """)
    List<Thesis> findAllStudentTheses(@Param("studentId") int studentId);
}
