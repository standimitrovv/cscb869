package com.example.GraduationSystem.repository;

import com.example.GraduationSystem.model.thesisRequest.ThesisRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThesisRequestRepository extends JpaRepository<ThesisRequest, Integer> {
}
