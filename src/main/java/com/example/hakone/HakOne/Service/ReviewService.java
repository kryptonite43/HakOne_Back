package com.example.hakone.HakOne.Service;

import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.domain.academy.AcademyRepository;
import com.example.hakone.HakOne.domain.review.Review;
import com.example.hakone.HakOne.domain.review.ReviewRepository;
import com.example.hakone.HakOne.domain.user.User;
import com.example.hakone.HakOne.domain.user.UserRepository;
import com.example.hakone.HakOne.dto.CreateReviewReqDto;
import com.example.hakone.HakOne.dto.ReviewByUserDto;
import com.example.hakone.HakOne.dto.ReviewResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final AcademyRepository academyRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final S3FileUploadService s3FileUploadService;

    @Transactional
    public Boolean createReview(CreateReviewReqDto createReviewReqDto, Long userId, Long academyId) throws IOException {
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
            System.out.println("==============receipt getname: "+receipt.getName());
            System.out.println("==============receipt getORiginalname: "+receipt.getOriginalFilename());
            // receipt를 S3에 올린 후 해당 파일의 링크를 String으로 받아옴. Review 객체의 receipt 필드로 저장하는 로직.
            String receiptLink = s3FileUploadService.UploadImageAndGetLink(receipt);

            Review newReview = Review.builder()
                    .member(member)
                    .academy(academy)
                    .content(content)
                    .score(score)
                    .receipt(receiptLink)
                    .build();
            reviewRepository.save(newReview);

            // 리뷰 등록에 따른 평균 점수, 리뷰 갯수 변경
            academy.updateReviewCountAndAvgScore();
            return true;
        }
    }

    @Transactional
    public List<ReviewResDto> getAllReviewByAcademy(Long academyId) {
        return reviewRepository.findAllByAcademy_Id(academyId).stream()
                .map(review -> {
                    ReviewResDto dto = new ReviewResDto();

                    dto.setReview_id(review.getId());
                    dto.setProfile_pic(review.getMember().getProfile_pic());
                    dto.setUsername(review.getMember().getName());
                    dto.setScore(review.getScore());
                    String dateTimeString = review.getCreatedDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
                    dto.setCreated_date(dateTimeString);
                    dto.setContent(review.getContent());

                    return dto;
                }).collect(Collectors.toList());
    }

    @Transactional
    public List<ReviewByUserDto> getAllReviewByUser(Long userId) {
        return reviewRepository.findAllByMember_Id(userId).stream()
                .map(review -> {
                    ReviewByUserDto dto = new ReviewByUserDto();

                    dto.setAcademy_id(review.getAcademy().getId());
                    dto.setReview_id(review.getId());
                    dto.setAcademy_name(review.getAcademy().getAcademyName());
                    dto.setAvg_score(review.getAcademy().getAvg_score());
                    dto.setReview_count(review.getAcademy().getReview_count());
                    dto.setContent(review.getContent());
                    dto.setUser_score(review.getScore());
                    String dateTimeString = review.getCreatedDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
                    dto.setCreated_date(dateTimeString);

                    return dto;
                }).collect(Collectors.toList());
    }

    @Transactional
    public Boolean deleteReview(Long academyId, Long reviewId) {
        boolean isReviewPresent = reviewRepository.findByAcademy_IdAndId(academyId, reviewId).isPresent();
        if (isReviewPresent) {
            Review review = reviewRepository.findByAcademy_IdAndId(academyId, reviewId).get();
            review.getMember().getReviews().remove(review);
            review.getAcademy().getReviews().remove(review);
            reviewRepository.delete(review);
            Academy academy = academyRepository.findById(academyId).get();
            academy.updateReviewCountAndAvgScore();
            return true;
        }
        else {
            return false;
        }
    }
}
