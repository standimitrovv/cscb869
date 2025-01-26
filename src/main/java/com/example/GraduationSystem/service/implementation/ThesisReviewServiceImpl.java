package com.example.GraduationSystem.service.implementation;

import com.example.GraduationSystem.dto.thesisReview.ThesisReviewDto;
import com.example.GraduationSystem.dto.thesisReview.ThesisReviewDtoResponse;
import com.example.GraduationSystem.dto.thesisReview.UpdateThesisReviewConclusionDto;
import com.example.GraduationSystem.dto.thesisReview.UpdateThesisReviewDto;
import com.example.GraduationSystem.model.Student;
import com.example.GraduationSystem.model.Thesis;
import com.example.GraduationSystem.model.lecturer.Lecturer;
import com.example.GraduationSystem.model.thesisReview.ThesisReview;
import com.example.GraduationSystem.model.thesisReview.ThesisReviewConclusion;
import com.example.GraduationSystem.repository.LecturerRepository;
import com.example.GraduationSystem.repository.ThesisRepository;
import com.example.GraduationSystem.repository.ThesisReviewRepository;
import com.example.GraduationSystem.service.ThesisReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ThesisReviewServiceImpl implements ThesisReviewService {
    private final ThesisReviewRepository thesisReviewRepository;
    private final ThesisRepository thesisRepository;
    private final LecturerRepository lecturerRepository;

    private final ModelMapper modelMapper;

    private final String INITIAL_REVIEW_CONTENT = "Temporary Content";

    public ThesisReviewServiceImpl(ThesisReviewRepository thesisReviewRepository, ThesisRepository thesisRepository, LecturerRepository lecturerRepository) {
        this.thesisReviewRepository = thesisReviewRepository;
        this.thesisRepository = thesisRepository;
        this.lecturerRepository = lecturerRepository;

        this.modelMapper = new ModelMapper();
    }

    @Override
    public ThesisReviewDtoResponse createReview(ThesisReviewDto thesisReviewDto) {
        int thesisId = thesisReviewDto.getThesisId();
        Thesis thesis = this.thesisRepository.findById(thesisId)
                .orElseThrow(() -> new IllegalArgumentException("Thesis not found with ID: " + thesisId));

        int lecturerId = thesisReviewDto.getReviewerId();
        Lecturer lecturer = this.lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new IllegalArgumentException("Lecturer not found with ID: " + lecturerId));

        ThesisReview review = new ThesisReview();

        review.setContent(this.INITIAL_REVIEW_CONTENT);
        review.setConclusion(ThesisReviewConclusion.PENDING);
        review.setUploadDate(LocalDate.now());
        review.setThesis(thesis);
        review.setReviewer(lecturer);

        return this.mapToDtoResponse(this.thesisReviewRepository.save(review));
    }

    @Override
    public ThesisReviewDtoResponse updateThesisReview(int thesisReviewId, UpdateThesisReviewDto dto) {
        ThesisReview review = this.thesisReviewRepository.findById(thesisReviewId)
                .orElseThrow(() -> new IllegalArgumentException("A thesis review not found with ID: " + thesisReviewId));

        if(Objects.equals(dto.getContent(), this.INITIAL_REVIEW_CONTENT) || review.getConclusion() == dto.getConclusion()) {
            throw new IllegalArgumentException("You have to provide a different conclusion than the current one");
        }

        if(Objects.equals(review.getConclusion().toString(), this.INITIAL_REVIEW_CONTENT)) {
            throw new Error("You have to set the content first before updating the conclusion");
        }

        review.setConclusion(dto.getConclusion());
        review.setContent(dto.getContent());

        return this.mapToDtoResponse(this.thesisReviewRepository.save(review));
    }

    @Override
    public void updateThesisReviewConclusion(int thesisReviewId, UpdateThesisReviewConclusionDto thesisReviewConclusionDto) {
        ThesisReview review = this.thesisReviewRepository.findById(thesisReviewId)
                .orElseThrow(() -> new IllegalArgumentException("A thesis review not found with ID: " + thesisReviewId));

        if(review.getConclusion() == thesisReviewConclusionDto.getConclusion()) {
            throw new IllegalArgumentException("You have to provide a different conclusion than the current one!");
        }

        if(Objects.equals(review.getConclusion().toString(), this.INITIAL_REVIEW_CONTENT)) {
            throw new Error("You have to set the content first before updating the conclusion");
        }

        review.setConclusion(thesisReviewConclusionDto.getConclusion());

        this.thesisReviewRepository.save(review);
    }

    @Override
    public List<Student> getStudentsWithNegativeReviews() {
        return this.thesisReviewRepository.findStudentsWithNegativeReviews();
    }

    @Override
    public Optional<ThesisReviewDtoResponse> getThesisReviewByThesisId(int thesisId) {
        return this.thesisReviewRepository
                .getThesisReviewByThesisId(thesisId)
                .map(this::mapToDtoResponse);
    }

    private ThesisReviewDtoResponse mapToDtoResponse(ThesisReview review){
        return this.modelMapper.map(review, ThesisReviewDtoResponse.class);
    }
}
