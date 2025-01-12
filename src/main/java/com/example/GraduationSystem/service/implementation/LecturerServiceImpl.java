package com.example.GraduationSystem.service.implementation;

import com.example.GraduationSystem.dto.lecturer.LecturerDtoResponse;
import com.example.GraduationSystem.dto.lecturer.UpdateLecturerPositionDto;
import com.example.GraduationSystem.exception.lecturer.LecturerNotFoundException;
import com.example.GraduationSystem.model.lecturer.Lecturer;
import com.example.GraduationSystem.model.lecturer.LecturerPosition;
import com.example.GraduationSystem.model.user.User;
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

    public Lecturer createLecturer(String name, User user) {
        if(name.isEmpty() || user == null) {
            throw new IllegalArgumentException("The name or the user is empty!");
        }

        Lecturer lecturer = new Lecturer();
        lecturer.setName(name);
        lecturer.setPosition(LecturerPosition.ASSISTANT);
        lecturer.setUser(user);

        return this.lecturerRepository.save(lecturer);
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
    public LecturerDtoResponse updateLecturerPosition(int lecturerId, UpdateLecturerPositionDto positionDto) {
        Lecturer lecturer = this.findLecturerByIdOrThrow(lecturerId);

        if(lecturer.getPosition() == positionDto.getPosition()){
            throw new IllegalArgumentException("You have to provide a different position than the current one!");
        }

        lecturer.setPosition(positionDto.getPosition());

        return this.mapToDtoResponse(lecturerRepository.save(lecturer));
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
