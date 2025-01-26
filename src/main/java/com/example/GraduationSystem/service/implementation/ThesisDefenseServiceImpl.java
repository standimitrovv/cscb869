package com.example.GraduationSystem.service.implementation;

import com.example.GraduationSystem.dto.student.StudentDtoResponse;
import com.example.GraduationSystem.dto.thesisDefense.*;
import com.example.GraduationSystem.dto.thesisReview.ThesisReviewDtoResponse;
import com.example.GraduationSystem.model.Student;
import com.example.GraduationSystem.model.Thesis;
import com.example.GraduationSystem.model.lecturer.Lecturer;
import com.example.GraduationSystem.model.thesisDefense.ThesisDefense;
import com.example.GraduationSystem.model.thesisDefense.ThesisDefenseGrade;
import com.example.GraduationSystem.model.thesisDefense.ThesisDefenseStatus;
import com.example.GraduationSystem.model.thesisReview.ThesisReviewConclusion;
import com.example.GraduationSystem.repository.*;
import com.example.GraduationSystem.service.ThesisDefenseService;
import com.example.GraduationSystem.service.ThesisReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ThesisDefenseServiceImpl implements ThesisDefenseService {
    private final ThesisDefenseRepository thesisDefenseRepository;
    private final ThesisRepository thesisRepository;
    private final ThesisReviewService thesisReviewService;

    private final StudentRepository studentRepository;
    private final LecturerRepository lecturerRepository;

    private final ModelMapper modelMapper;

    public ThesisDefenseServiceImpl(ThesisDefenseRepository thesisDefenseRepository, StudentRepository studentRepository, LecturerRepository lecturerRepository, ThesisRepository thesisRepository, ThesisReviewService thesisReviewService) {
        this.thesisDefenseRepository = thesisDefenseRepository;
        this.studentRepository = studentRepository;
        this.lecturerRepository = lecturerRepository;
        this.thesisRepository = thesisRepository;
        this.thesisReviewService = thesisReviewService;

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

        boolean hasThesisDefenses = !(theses.stream().filter((t) -> t.getDefense() != null).toList().isEmpty());
        if(hasThesisDefenses) {
           throw new IllegalArgumentException("Some of the theses you provided already have an assigned defense");
        }

        theses.forEach((t) -> {
            Optional<ThesisReviewDtoResponse> thesisReview = this.thesisReviewService.getThesisReviewByThesisId(t.getId());
            if(thesisReview.isEmpty()) {
                throw new IllegalArgumentException("Some of the theses you provided must go through a review first");
            }

            if(thesisReview.get().getConclusion() != ThesisReviewConclusion.POSITIVE) {
                throw new IllegalArgumentException("Some of the theses you provided need a review approval before creating a defense");
            }
        });

        ThesisDefense defense = new ThesisDefense();

        defense.setDate(LocalDate.now());
        defense.setStudents(students);
        defense.setLecturers(lecturers);
        defense.setStatus(ThesisDefenseStatus.PLANNED);
        defense.setTheses(theses);
        ThesisDefense savedDefense = this.thesisDefenseRepository.save(defense);

        for (Thesis thesis : theses) {
            thesis.setDefense(savedDefense);
            thesisRepository.save(thesis);
        }

        savedDefense.setTheses(theses);

        return this.mapToDtoResponse(savedDefense);
    }

    @Override
    public List<ThesisDefenseDtoResponse> getDefenses() {
        return this.thesisDefenseRepository.findAll()
                .stream()
                .map(this::mapToDtoResponse)
                .toList();
    }

    @Override
    public ThesisDefenseDtoResponse getDefense(int defenseId) {
        ThesisDefense defense = this.thesisDefenseRepository.findById(defenseId)
                .orElseThrow(() -> new IllegalArgumentException("Thesis defense with ID: " + defenseId + " was not found."));

        return this.mapToDtoResponse(defense);
    }

    @Override
    public void cancelDefense(int defenseId) {
        ThesisDefense defense = this.thesisDefenseRepository.findById(defenseId)
                .orElseThrow(() -> new IllegalArgumentException("Thesis defense with ID: " + defenseId + " was not found."));

        ThesisDefenseStatus status = defense.getStatus();
        if(status == ThesisDefenseStatus.COMPLETED || status == ThesisDefenseStatus.CANCELED) throw new IllegalArgumentException("You can't cancel a completed or an already canceled defense!");

        defense.setStatus(ThesisDefenseStatus.CANCELED);
        thesisDefenseRepository.save(defense);
    }

    public void updateDefenseStatus(int defenseId, UpdateThesisDefenseStatusDto status) {
        ThesisDefense defense = this.thesisDefenseRepository.findById(defenseId)
                .orElseThrow(() -> new IllegalArgumentException("Thesis defense with ID: " + defenseId + " was not found."));

        if(status.getStatus() == defense.getStatus()) {
            throw new IllegalArgumentException("You have to provide a different status than the current one!");
        }

        if(defense.getStatus() == ThesisDefenseStatus.COMPLETED || defense.getStatus() == ThesisDefenseStatus.CANCELED) {
            throw new IllegalArgumentException("You can't change the status of an already completed or canceled defense!");
        }

        defense.setStatus(status.getStatus());
        this.thesisDefenseRepository.save(defense);
    }

    @Override
    public ThesisDefenseDtoResponse addStudent(int defenseId, int studentId) {
        ThesisDefense defense = this.thesisDefenseRepository.findById(defenseId)
                .orElseThrow(() -> new IllegalArgumentException("Thesis defense not found with ID: " + defenseId));

        ThesisDefenseStatus status = defense.getStatus();
        if(status == ThesisDefenseStatus.COMPLETED || status == ThesisDefenseStatus.CANCELED) {
            throw new IllegalArgumentException("You can't add a student to an already completed or canceled defense!");
        }

        Student student = this.studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

        defense.getStudents().add(student);

        return this.mapToDtoResponse(this.thesisDefenseRepository.save(defense));
    }

    @Override
    public ThesisDefenseDtoResponse addLecturer(int defenseId, int lecturerId) {
        ThesisDefense defense = this.thesisDefenseRepository.findById(defenseId)
                .orElseThrow(() -> new IllegalArgumentException("Thesis defense not found with ID: " + defenseId));

        ThesisDefenseStatus status = defense.getStatus();
        if(status == ThesisDefenseStatus.COMPLETED || status == ThesisDefenseStatus.CANCELED) {
            throw new IllegalArgumentException("You can't add a lecturer to an already completed or canceled defense!");
        }

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

        ThesisDefenseStatus status = defense.getStatus();
        if(status == ThesisDefenseStatus.COMPLETED || status == ThesisDefenseStatus.CANCELED) {
            throw new IllegalArgumentException("You can't assign a grade to an already completed or canceled defense!");
        }

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
    public List<StudentDtoResponse> getGraduatedStudentsInPeriod(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null.");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date.");
        }

        return this.thesisDefenseRepository.findGraduatedStudentsInPeriod(startDate, endDate)
                .stream()
                .map(s -> this.modelMapper.map(s, StudentDtoResponse.class))
                .toList();
    }

    @Override
    public List<StudentDtoResponse> getStudentsInDefensePeriod(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null.");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date.");
        }

        return this.thesisDefenseRepository.findStudentsAppearedInDefensesBetween(startDate, endDate)
                .stream().map((s) -> this.modelMapper.map(s, StudentDtoResponse.class)).toList();
    }

    @Override
    public long getSuccessfulDefensesByLecturer(int lecturerId) {
        this.lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new IllegalArgumentException("A lecturer with id: " + lecturerId + " doesn't exist."));

        boolean isLecturerAssignedToAnyDefense = this.thesisDefenseRepository.isLecturerAssignedToAnyDefense(lecturerId);
        if(!isLecturerAssignedToAnyDefense) throw new IllegalArgumentException("A lecturer with id: " + lecturerId + " is not assigned to any thesis defense.");

        return this.thesisDefenseRepository.countSuccessfulDefensesByLecturer(lecturerId);
    }

    @Override
    public Optional<ThesisDefenseDetailsDtoResponse> getThesisDefenseDetails(int thesisId) {
        Optional<Object[]> defense = this.thesisDefenseRepository.findThesisDefensesByThesisId(thesisId).stream().findFirst();

        if(defense.isEmpty()) {
            return Optional.empty();
        }

        java.sql.Date sqlDate = (java.sql.Date) defense.get()[1];

        return Optional.of(new ThesisDefenseDetailsDtoResponse(
                (int) defense.get()[0],
                (LocalDate) sqlDate.toLocalDate(),
                ThesisDefenseStatus.valueOf((String) defense.get()[2]),
                (double) defense.get()[3]
        ));
    }

    private ThesisDefenseDtoResponse mapToDtoResponse(ThesisDefense defense){
        return this.modelMapper.map(defense, ThesisDefenseDtoResponse.class);
    }
}
