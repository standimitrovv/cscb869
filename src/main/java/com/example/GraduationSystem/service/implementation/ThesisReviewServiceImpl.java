package com.example.GraduationSystem.service.implementation;

import com.example.GraduationSystem.dto.thesisReview.ThesisReviewDto;
import com.example.GraduationSystem.dto.thesisReview.ThesisReviewDtoResponse;
import com.example.GraduationSystem.dto.thesisReview.UpdateThesisReviewConclusionDto;
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

@Service
public class ThesisReviewServiceImpl implements ThesisReviewService {
    private final ThesisReviewRepository thesisReviewRepository;
    private final ThesisRepository thesisRepository;
    private final LecturerRepository lecturerRepository;

    private final ModelMapper modelMapper;

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

        review.setContent(thesisReviewDto.getContent());
        review.setConclusion(ThesisReviewConclusion.PENDING);
        review.setUploadDate(LocalDate.now());
        review.setThesis(thesis);
        review.setReviewer(lecturer);

        return this.mapToDtoResponse(this.thesisReviewRepository.save(review));
    }

    @Override
    public void updateThesisReviewConclusion(int thesisReviewId, UpdateThesisReviewConclusionDto thesisReviewConclusionDto) {
        ThesisReview review = this.thesisReviewRepository.findById(thesisReviewId)
                .orElseThrow(() -> new IllegalArgumentException("A thesis review not found with ID: " + thesisReviewId));

        review.setConclusion(thesisReviewConclusionDto.getConclusion());

        this.thesisReviewRepository.save(review);
    }

    @Override
    public List<Student> getStudentsWithNegativeReviews() {
        return this.thesisReviewRepository.findStudentsWithNegativeReviews();
    }

    private ThesisReviewDtoResponse mapToDtoResponse(ThesisReview review){
        return this.modelMapper.map(review, ThesisReviewDtoResponse.class);
    }
}
