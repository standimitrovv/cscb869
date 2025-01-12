package com.example.GraduationSystem.service.implementation;

import com.example.GraduationSystem.dto.student.StudentDtoResponse;
import com.example.GraduationSystem.exception.student.StudentNotFoundException;
import com.example.GraduationSystem.model.Student;
import com.example.GraduationSystem.model.user.User;
import com.example.GraduationSystem.repository.StudentRepository;
import com.example.GraduationSystem.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        this.modelMapper = new ModelMapper();
    }

    public Student createStudent(String name, User user) {
        if(name.isEmpty() || user == null) {
            throw new IllegalArgumentException("The name or the user is empty!");
        }

        Student student = new Student();
        student.setName(name);
        student.setFacultyNumber(UUID.randomUUID().toString());
        student.setUser(user);

        return this.studentRepository.save(student);
    }

    public List<StudentDtoResponse> getStudents(){
        List<Student> students = this.studentRepository.findAll();

        return students
                .stream()
                .map(this::mapToDtoResponse)
                .collect(Collectors.toList());
    }

    public StudentDtoResponse getStudent(int studentId) {
        return this.mapToDtoResponse(this.findStudentByIdOrThrow(studentId));
    }

    public void deleteStudent(int studentId){
        Student student = this.findStudentByIdOrThrow(studentId);

        this.studentRepository.delete(student);
    }

    private Student findStudentByIdOrThrow (int studentId) {
        return this.studentRepository.findById(studentId)
                .orElseThrow(StudentNotFoundException::new);
    }

    private StudentDtoResponse mapToDtoResponse(Student student){
        return this.modelMapper.map(student, StudentDtoResponse.class);
    }
}
