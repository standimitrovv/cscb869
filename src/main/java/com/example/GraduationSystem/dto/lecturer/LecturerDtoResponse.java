package com.example.GraduationSystem.dto.lecturer;

import com.example.GraduationSystem.model.lecturer.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LecturerDtoResponse {
    private int id;

    private String name;

    private Position position;
}
