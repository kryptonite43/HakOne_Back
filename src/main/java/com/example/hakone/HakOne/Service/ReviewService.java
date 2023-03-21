package com.example.hakone.HakOne.Service;

import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.domain.academy.AcademyRepository;
import com.example.hakone.HakOne.domain.review.Review;
import com.example.hakone.HakOne.domain.review.ReviewRepository;
import com.example.hakone.HakOne.domain.user.User;
import com.example.hakone.HakOne.domain.user.UserRepository;
import com.example.hakone.HakOne.dto.CreateReviewReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final AcademyRepository academyRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public Boolean createReview(CreateReviewReqDto createReviewReqDto, Long userId, Long academyId) {
        User member = userRepository.findById(userId).get();
        Academy academy = academyRepository.findById(academyId).get();
        boolean isReviewPresent = reviewRepository.findByMember_IdAndAcademy_Id(userId, academyId).isPresent();
        if (isReviewPresent) {
            return false;
        }
        else {
            MultipartFile receipt = createReviewReqDto.getReceipt();
            float score = createReviewReqDto.getScore();
            String content = createReviewReqDto.getContent();
            Review newReview = Review.builder()
                    .member(member)
                    .academy(academy)
                    .content(content)
                    .score(score)
                    .receipt(receipt.getOriginalFilename())
                    .build();
            reviewRepository.save(newReview);

            return true;
        }
    }
}
