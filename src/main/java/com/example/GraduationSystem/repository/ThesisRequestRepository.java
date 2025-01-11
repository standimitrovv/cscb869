package com.example.GraduationSystem.repository;

import com.example.GraduationSystem.model.thesisRequest.ThesisRequest;
import com.example.GraduationSystem.model.thesisRequest.ThesisRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThesisRequestRepository extends JpaRepository<ThesisRequest, Integer> {
    List<ThesisRequest> findByStatus(ThesisRequestStatus status);

    List<ThesisRequest> findBySupervisorIdAndStatus(int supervisorId, ThesisRequestStatus status);

    @Query("""
        SELECT DISTINCT tr
        FROM ThesisRequest tr
        JOIN tr.student s
        WHERE s.id = :studentId
    """)
    List<ThesisRequest> findStudentTheses(@Param("studentId") int studentId);
}
