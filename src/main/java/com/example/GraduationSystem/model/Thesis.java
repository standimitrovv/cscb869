package com.example.GraduationSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.GraduationSystem.model.thesisRequest.ThesisRequest;

import java.time.LocalDate;

@Entity
@Table(name = "theses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Thesis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="title", nullable = false)
    private String title;

    @Lob
    @Column(name="content", columnDefinition="text", nullable = false)
    private String content;

    @Column(name="uploadDate", nullable = false)
    private LocalDate uploadDate;

    @OneToOne
    @JoinColumn(name = "thesis_request_id", referencedColumnName = "id", nullable = false)
    private ThesisRequest thesisRequest;
}
