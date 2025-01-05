package com.example.GraduationSystem.repository;

import com.example.GraduationSystem.model.thesisReview.ThesisReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThesisReviewRepository extends JpaRepository<ThesisReview, Integer> {
}
