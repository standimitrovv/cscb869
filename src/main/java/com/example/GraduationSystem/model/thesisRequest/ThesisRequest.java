package com.example.GraduationSystem.model.thesisRequest;


import com.example.GraduationSystem.model.Student;
import com.example.GraduationSystem.model.lecturer.Lecturer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "thesis_requests")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThesisRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="topic", nullable = false)
    private String topic;

    @Column(name="goal", nullable = false)
    private String goal;

    @Column(name="tasks", nullable = false)
    private String tasks;

    @Column(name="technologies", nullable = false)
    private String technologies;

    @Column(name="submission_date", nullable = false)
    private LocalDate submissionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    private Lecturer supervisor;

    @Enumerated(EnumType.STRING)
    private ThesisRequestStatus status;
}
