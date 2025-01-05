package com.example.GraduationSystem.model.thesisReview;

import com.example.GraduationSystem.model.Thesis;
import com.example.GraduationSystem.model.lecturer.Lecturer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "thesis_reviews")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThesisReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "upload_date", nullable = false)
    private LocalDate uploadDate;

    @Lob
    @Column(name = "content", columnDefinition = "text", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "conclusion", nullable = false)
    private ThesisReviewConclusion conclusion;

    @ManyToOne(fetch = FetchType.LAZY)
    private Thesis thesis;

    @ManyToOne(fetch = FetchType.LAZY)
    private Lecturer reviewer;
}
