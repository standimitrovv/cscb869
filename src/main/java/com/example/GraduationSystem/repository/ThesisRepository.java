package com.example.GraduationSystem.repository;

import com.example.GraduationSystem.model.Thesis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThesisRepository extends JpaRepository<Thesis, Integer> {
}
