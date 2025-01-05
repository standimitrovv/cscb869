package com.example.GraduationSystem.model.thesisDefense;

import com.example.GraduationSystem.model.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "thesis_defense_grades")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThesisDefenseGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ThesisDefense defense;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @Column(name = "grade", nullable = false)
    private double grade;
}
