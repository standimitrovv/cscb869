package com.example.GraduationSystem.repository;

import com.example.GraduationSystem.model.Student;
import com.example.GraduationSystem.model.thesisReview.ThesisReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThesisReviewRepository extends JpaRepository<ThesisReview, Integer> {
    @Query("""
    SELECT DISTINCT s
    FROM Student s
    JOIN ThesisRequest tr ON s.id = tr.student.id
    JOIN Thesis t ON tr.id = t.thesisRequest.id
    JOIN ThesisReview r ON t.id = r.thesis.id
    WHERE r.conclusion = 'NEGATIVE'
""")
    List<Student> findStudentsWithNegativeReviews();
}
