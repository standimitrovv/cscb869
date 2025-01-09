package com.example.GraduationSystem.service.implementation;

import com.example.GraduationSystem.dto.student.StudentDtoResponse;
import com.example.GraduationSystem.dto.thesisDefense.ThesisDefenseDto;
import com.example.GraduationSystem.dto.thesisDefense.ThesisDefenseDtoResponse;
import com.example.GraduationSystem.dto.thesisDefense.UpdateThesisDefenseGradeDto;
import com.example.GraduationSystem.model.Student;
import com.example.GraduationSystem.model.Thesis;
import com.example.GraduationSystem.model.lecturer.Lecturer;
import com.example.GraduationSystem.model.thesisDefense.ThesisDefense;
import com.example.GraduationSystem.model.thesisDefense.ThesisDefenseGrade;
import com.example.GraduationSystem.repository.LecturerRepository;
import com.example.GraduationSystem.repository.StudentRepository;
import com.example.GraduationSystem.repository.ThesisDefenseRepository;
import com.example.GraduationSystem.repository.ThesisRepository;
import com.example.GraduationSystem.service.ThesisDefenseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ThesisDefenseServiceImpl implements ThesisDefenseService {
    private final ThesisDefenseRepository thesisDefenseRepository;
    private final StudentRepository studentRepository;
    private final LecturerRepository lecturerRepository;
    private final ThesisRepository thesisRepository;

    private final ModelMapper modelMapper;

    public ThesisDefenseServiceImpl(ThesisDefenseRepository thesisDefenseRepository, StudentRepository studentRepository, LecturerRepository lecturerRepository, ThesisRepository thesisRepository) {
        this.thesisDefenseRepository = thesisDefenseRepository;
        this.studentRepository = studentRepository;
        this.lecturerRepository = lecturerRepository;
        this.thesisRepository = thesisRepository;

        this.modelMapper = new ModelMapper();
    }

    @Override
    public ThesisDefenseDtoResponse createDefense(ThesisDefenseDto thesisDefenseDto) {
        List<Student> students = this.studentRepository.findAllById(thesisDefenseDto.getStudentIds());
        if (students.size() != thesisDefenseDto.getStudentIds().size()) {
            throw new IllegalArgumentException("Some students were not found.");
        }

        List<Lecturer> lecturers = this.lecturerRepository.findAllById(thesisDefenseDto.getLecturerIds());
        if (lecturers.size() != thesisDefenseDto.getLecturerIds().size()) {
            throw new IllegalArgumentException("Some lecturers were not found.");
        }

        List<Thesis> theses = this.thesisRepository.findAllById(thesisDefenseDto.getThesisIds());
        if (theses.size() != thesisDefenseDto.getThesisIds().size()) {
            throw new IllegalArgumentException("Some theses were not found.");
        }

        ThesisDefense defense = new ThesisDefense();

        defense.setDate(LocalDate.now());
        defense.setStudents(students);
        defense.setLecturers(lecturers);

        for (Thesis thesis : theses) {
            thesis.setDefense(defense);
        }

        //        this.thesisRepository.saveAll(theses);
        defense.setTheses(theses);
        ThesisDefense savedDefense = this.thesisDefenseRepository.save(defense);

        return this.mapToDtoResponse(savedDefense);
    }

    @Override
    public ThesisDefenseDtoResponse addStudent(int defenseId, int studentId) {
        ThesisDefense defense = this.thesisDefenseRepository.findById(defenseId)
                .orElseThrow(() -> new IllegalArgumentException("Thesis defense not found with ID: " + defenseId));

        Student student = this.studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

        defense.getStudents().add(student);

        return this.mapToDtoResponse(this.thesisDefenseRepository.save(defense));
    }

    @Override
    public ThesisDefenseDtoResponse addLecturer(int defenseId, int lecturerId) {
        ThesisDefense defense = this.thesisDefenseRepository.findById(defenseId)
                .orElseThrow(() -> new IllegalArgumentException("Thesis defense not found with ID: " + defenseId));

        Lecturer lecturer = this.lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new IllegalArgumentException("Lecturer not found with ID: " + lecturerId));

        defense.getLecturers().add(lecturer);

        return this.mapToDtoResponse(this.thesisDefenseRepository.save(defense));
    }

    @Override
    public ThesisDefenseDtoResponse assignStudentGrade(int defenseId, int studentId, UpdateThesisDefenseGradeDto gradeDto) {
        if (gradeDto.getGrade() < 2.0 || gradeDto.getGrade() > 6.0) {
            throw new IllegalArgumentException("Grade must be between 2.0 and 6.0.");
        }

        ThesisDefense defense = this.thesisDefenseRepository.findById(defenseId)
                .orElseThrow(() -> new IllegalArgumentException("Thesis defense not found with ID: " + defenseId));

        this.studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

        Student defendingStudent = defense.getStudents().stream()
                .filter(s -> s.getId() == studentId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Student with ID: " + studentId + " is not part of the defense."));

        Optional<ThesisDefenseGrade> studentDefenseGrade = defense.getGrades().stream()
                .filter((g) -> g.getStudent().getId() == studentId)
                .findFirst();

        if(studentDefenseGrade.isPresent()) {
            throw new IllegalArgumentException("The student you are trying to assign a grade to already has one!. Student ID: " + studentId);
        }

        ThesisDefenseGrade defenseGrade = new ThesisDefenseGrade();

        defenseGrade.setStudent(defendingStudent);
        defenseGrade.setDefense(defense);
        defenseGrade.setGrade(gradeDto.getGrade());

        defense.getGrades().add(defenseGrade);

        return this.mapToDtoResponse(this.thesisDefenseRepository.save(defense));
    }

    @Override
    public List<StudentDtoResponse> getStudentsInDefensePeriod(LocalDate startDate, LocalDate endDate) {
        List<Student> students = this.thesisDefenseRepository.findStudentsAppearedInDefensesBetween(startDate, endDate);

        return students.stream().map((s) -> this.modelMapper.map(s, StudentDtoResponse.class)).toList();
    }

    @Override
    public long getSuccessfulDefensesByLecturer(int lecturerId) {
        this.lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new IllegalArgumentException("A lecturer with id: " + lecturerId + " doesn't exist."));

        boolean isLecturerAssignedToAnyDefense = this.thesisDefenseRepository.isLecturerAssignedToAnyDefense(lecturerId);
        if(!isLecturerAssignedToAnyDefense) throw new IllegalArgumentException("A lecturer with id: " + lecturerId + " is not assigned to any thesis defense.");

        return this.thesisDefenseRepository.countSuccessfulDefensesByLecturer(lecturerId);
    }

    private ThesisDefenseDtoResponse mapToDtoResponse(ThesisDefense defense){
        return this.modelMapper.map(defense, ThesisDefenseDtoResponse.class);
    }
}
