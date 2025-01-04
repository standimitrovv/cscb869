package com.example.GraduationSystem.dto.thesisRequest;

import com.example.GraduationSystem.model.thesisRequest.ThesisRequestStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateThesisRequestStatusDto {
    @NotNull(message = "The 'thesisRequestStatus' field cannot be null!")
    @Enumerated(EnumType.STRING)
    private ThesisRequestStatus thesisRequestStatus;
}
