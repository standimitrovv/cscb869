package com.example.GraduationSystem.service.implementation;

import com.example.GraduationSystem.dto.lecturer.LecturerDto;
import com.example.GraduationSystem.dto.lecturer.LecturerDtoResponse;
import com.example.GraduationSystem.exception.lecturer.LecturerNotFoundException;
import com.example.GraduationSystem.model.lecturer.Lecturer;
import com.example.GraduationSystem.repository.LecturerRepository;
import com.example.GraduationSystem.service.LecturerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LecturerServiceImpl implements LecturerService {
    private final LecturerRepository lecturerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    LecturerServiceImpl(LecturerRepository lecturerRepository){
        this.lecturerRepository = lecturerRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public LecturerDtoResponse createLecturer(LecturerDto lecturerDto) {
        Lecturer lecturer = new Lecturer();

        lecturer.setName(lecturerDto.getName());
        lecturer.setPosition(lecturerDto.getPosition());

        return this.mapToDtoResponse(this.lecturerRepository.save(lecturer));
    }

    @Override
    public List<LecturerDtoResponse> getLecturers() {
        List<Lecturer> lecturers = this.lecturerRepository.findAll();

        return lecturers
                .stream()
                .map(this::mapToDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public LecturerDtoResponse getLecturer(int lecturerId) {
        return this.mapToDtoResponse(this.findLecturerByIdOrThrow(lecturerId));
    }

    @Override
    public LecturerDtoResponse updateLecturer(int lecturerId, LecturerDto lecturerDto) {
        Lecturer lecturer = this.findLecturerByIdOrThrow(lecturerId);

        lecturer.setName(lecturerDto.getName());
        lecturer.setPosition(lecturer.getPosition());

        return this.mapToDtoResponse(lecturer);
    }

    @Override
    public void deleteLecturer(int lecturerId) {
        Lecturer lecturer = this.findLecturerByIdOrThrow(lecturerId);

        this.lecturerRepository.delete(lecturer);
    }

    private Lecturer findLecturerByIdOrThrow (int lecturerId) {
        return this.lecturerRepository.findById(lecturerId)
                .orElseThrow(LecturerNotFoundException::new);
    }

    private LecturerDtoResponse mapToDtoResponse(Lecturer lecturer){
        return this.modelMapper.map(lecturer, LecturerDtoResponse.class);
    }
}
