package com.example.GraduationSystem.repository;

import com.example.GraduationSystem.model.thesisDefense.ThesisDefense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThesisDefenseRepository extends JpaRepository<ThesisDefense, Integer> {
}
