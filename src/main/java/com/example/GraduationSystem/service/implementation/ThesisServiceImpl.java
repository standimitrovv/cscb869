package com.example.GraduationSystem.service.implementation;

import com.example.GraduationSystem.dto.thesis.ThesisDto;
import com.example.GraduationSystem.dto.thesis.ThesisDtoResponse;
import com.example.GraduationSystem.dto.thesisRequest.ThesisRequestDtoResponse;
import com.example.GraduationSystem.model.Thesis;
import com.example.GraduationSystem.model.thesisRequest.ThesisRequestStatus;
import com.example.GraduationSystem.model.thesisRequest.ThesisRequest;
import com.example.GraduationSystem.repository.ThesisRepository;
import com.example.GraduationSystem.repository.ThesisRequestRepository;
import com.example.GraduationSystem.service.ThesisService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@Service
public class ThesisServiceImpl implements ThesisService {
    private final ThesisRepository thesisRepository;
    private final ThesisRequestRepository thesisRequestRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ThesisServiceImpl(ThesisRepository thesisRepository, ThesisRequestRepository thesisRequestRepository) {
        this.thesisRepository = thesisRepository;
        this.thesisRequestRepository = thesisRequestRepository;

        this.modelMapper = new ModelMapper();
    }

    @Override
    public ThesisDtoResponse createThesis(int thesisRequestId, ThesisDto thesisDto) {
        ThesisRequest thesisRequest = thesisRequestRepository.findById(thesisRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Thesis request not found with ID: " + thesisRequestId));

        if (thesisRequest.getStatus() != ThesisRequestStatus.APPROVED) {
            throw new IllegalStateException("The thesis request is not approved yet.");
        }

        Thesis thesis = new Thesis();

        thesis.setTitle(thesisDto.getTitle());
        thesis.setContent(thesisDto.getContent());
        thesis.setUploadDate(LocalDate.now());
        thesis.setThesisRequest(thesisRequest);
//        thesis.setReviews(new ArrayList<>());

        return this.mapToDtoResponse(this.thesisRepository.save(thesis));
    }

    @Override
    public List<ThesisDtoResponse> getThesisTitlesByKeyword(String keyword) {
        return thesisRepository.findByTitleContainingIgnoreCase(keyword).stream()
                .map(this::mapToDtoResponse)
                .toList();
    }

    private ThesisDtoResponse mapToDtoResponse(Thesis thesis){
        ThesisDtoResponse dto = this.modelMapper.map(thesis, ThesisDtoResponse.class);

        ThesisRequestDtoResponse thesisRequest = dto.getThesisRequest();
        if(thesisRequest != null) {
            dto.setThesisRequest(thesisRequest);
        }

        return dto;
    }
}
