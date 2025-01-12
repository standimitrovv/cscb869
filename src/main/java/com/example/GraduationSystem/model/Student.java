package com.example.GraduationSystem.model;

import com.example.GraduationSystem.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "students")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="facultyNumber", nullable = false, unique = true)
    private String facultyNumber;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
