package com.example.GraduationSystem.repository;

import com.example.GraduationSystem.model.Student;
import com.example.GraduationSystem.model.lecturer.Lecturer;
import com.example.GraduationSystem.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u.lecturer FROM User u WHERE u.email = :email AND u.role = 'LECTURER'")
    Optional<Lecturer> findLecturerByEmail(@Param("email") String email);

    @Query("SELECT u.student FROM User u WHERE u.email = :email AND u.role = 'STUDENT'")
    Optional<Student> findStudentByEmail(@Param("email") String email);
}
