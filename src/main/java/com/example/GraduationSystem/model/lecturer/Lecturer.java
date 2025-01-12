package com.example.GraduationSystem.model.lecturer;

import com.example.GraduationSystem.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lecturers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "position", nullable = false)
    @Enumerated(EnumType.STRING)
    private LecturerPosition position;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
