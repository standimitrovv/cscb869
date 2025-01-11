package com.example.GraduationSystem.service.implementation;

import com.example.GraduationSystem.dto.thesisRequest.ThesisRequestDto;
import com.example.GraduationSystem.dto.thesisRequest.ThesisRequestDtoResponse;
import com.example.GraduationSystem.dto.thesisRequest.UpdateThesisRequestStatusDto;
import com.example.GraduationSystem.model.Student;
import com.example.GraduationSystem.model.lecturer.Lecturer;
import com.example.GraduationSystem.model.thesisRequest.ThesisRequest;
import com.example.GraduationSystem.model.thesisRequest.ThesisRequestStatus;
import com.example.GraduationSystem.repository.LecturerRepository;
import com.example.GraduationSystem.repository.StudentRepository;
import com.example.GraduationSystem.repository.ThesisRequestRepository;
import com.example.GraduationSystem.service.ThesisRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ThesisRequestServiceImpl implements ThesisRequestService {
    private final ThesisRequestRepository thesisRequestRepository;
    private final StudentRepository studentRepository;
    private final LecturerRepository lecturerRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ThesisRequestServiceImpl(ThesisRequestRepository thesisRequestRepository, StudentRepository studentRepository, LecturerRepository lecturerRepository) {
        this.thesisRequestRepository = thesisRequestRepository;
        this.studentRepository = studentRepository;
        this.lecturerRepository = lecturerRepository;

        this.modelMapper = new ModelMapper();
    }

    public List<ThesisRequestDtoResponse> getApprovedThesisRequests() {
        return thesisRequestRepository.findByStatus(ThesisRequestStatus.APPROVED).stream()
                .map(this::mapToDtoResponse)
                .toList();
    }

    @Override
    public List<ThesisRequestDtoResponse> getApprovedThesisRequestsBySupervisor(int supervisorId) {
        lecturerRepository.findById(supervisorId)
                .orElseThrow(() -> new IllegalArgumentException("Supervisor not found with ID: " + supervisorId));

        return thesisRequestRepository.findBySupervisorIdAndStatus(supervisorId,ThesisRequestStatus.APPROVED)
                .stream()
                .map(this::mapToDtoResponse)
                .toList();
    }

    @Override
    public ThesisRequestDtoResponse createThesisRequest(ThesisRequestDto thesisRequestDto) {
        Student student = studentRepository.findById(thesisRequestDto.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + thesisRequestDto.getStudentId()));

        Lecturer supervisor = lecturerRepository.findById(thesisRequestDto.getSupervisorId())
                .orElseThrow(() -> new IllegalArgumentException("Supervisor not found with ID: " + thesisRequestDto.getSupervisorId()));

        ThesisRequest request = new ThesisRequest();

        request.setTopic(thesisRequestDto.getTopic());
        request.setGoal(thesisRequestDto.getGoal());
        request.setStatus(ThesisRequestStatus.PENDING);
        request.setTasks(thesisRequestDto.getTasks());
        request.setTechnologies(thesisRequestDto.getTechnologies());
        request.setSubmissionDate(LocalDate.now());

        request.setStudent(student);
        request.setSupervisor(supervisor);

        return this.mapToDtoResponse(thesisRequestRepository.save(request));
    }

    @Override
    public void updateThesisRequestStatus(int thesisRequestId, UpdateThesisRequestStatusDto newRequestStatusDto) {
        ThesisRequest request = thesisRequestRepository.findById(thesisRequestId)
                .orElseThrow(() -> new IllegalArgumentException("The thesis request with id: " + thesisRequestId + " was not found"));

        if(request.getStatus() != ThesisRequestStatus.PENDING) {
            throw new IllegalArgumentException("You can't change the status of an already approved, rejected or cancelled thesis request");
        }

        request.setStatus(newRequestStatusDto.getThesisRequestStatus());
        thesisRequestRepository.save(request);
    }

    @Override
    public List<ThesisRequestDtoResponse> getThesisRequests() {
        return this.thesisRequestRepository.findAll()
                .stream()
                .map(this::mapToDtoResponse)
                .toList();
    }

    @Override
    public ThesisRequestDtoResponse getThesisRequest(int thesisRequestId) {
        ThesisRequest request = thesisRequestRepository.findById(thesisRequestId)
                .orElseThrow(() -> new IllegalArgumentException("The thesis request with id: " + thesisRequestId + " was not found"));

        return this.mapToDtoResponse(request);
    }

    @Override
    public List<ThesisRequestDtoResponse> getStudentThesisRequests(int studentId) {
        return this.thesisRequestRepository.findStudentTheses(studentId)
                .stream()
                .map(this::mapToDtoResponse)
                .toList();
    }

    private ThesisRequestDtoResponse mapToDtoResponse(ThesisRequest request){
        return this.modelMapper.map(request, ThesisRequestDtoResponse.class);
    }
}
