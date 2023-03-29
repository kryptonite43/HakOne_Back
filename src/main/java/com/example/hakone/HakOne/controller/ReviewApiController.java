package com.example.hakone.HakOne.controller;

import com.example.hakone.HakOne.Service.ReviewService;
import com.example.hakone.HakOne.domain.LogDateTime;
import com.example.hakone.HakOne.dto.CreateReviewReqDto;
import com.example.hakone.HakOne.dto.ReviewByUserDto;
import com.example.hakone.HakOne.dto.ReviewResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewApiController {
    private final ReviewService reviewService;

    @PostMapping("/user/{user_id}/academy/{academy_id}")
    public ResponseEntity<String> createReview(@RequestPart MultipartFile userImage,
                                               @RequestPart("score") float score,
                                               @RequestPart("content") String content,
                                               @PathVariable Long user_id,
                                               @PathVariable Long academy_id) throws IOException {
        CreateReviewReqDto createReviewReqDto = new CreateReviewReqDto();
        createReviewReqDto.setReceipt(userImage);
        createReviewReqDto.setScore(score);
        createReviewReqDto.setContent(content);
        System.out.println("receipt: "+createReviewReqDto.getReceipt());
        System.out.println("content: "+createReviewReqDto.getContent());
        System.out.println("score: "+createReviewReqDto.getScore());
        if (reviewService.createReview(createReviewReqDto, user_id, academy_id)) {
            System.out.println("[CUSTOM LOG "+ new LogDateTime().getDate() + "]     리뷰 신규 등록");
            return ResponseEntity
                    .status(200)
                    .body("리뷰 등록 완료");
        }
        else {
            System.out.println("[CUSTOM LOG "+ new LogDateTime().getDate() + "]     이미 리뷰 등록됨");
            return ResponseEntity
                    .status(409)
                    .body("이미 리뷰 등록된 학원입니다");
        }
    }

    @GetMapping("/academy/{academy_id}/review")
    public ResponseEntity<List<ReviewResDto>> getAllReviewByAcademy(@PathVariable Long academy_id) {
        System.out.println("[CUSTOM LOG "+ new LogDateTime().getDate() + "]     해당 학원 리뷰목록 조회");
        return ResponseEntity.ok(reviewService.getAllReviewByAcademy(academy_id));
    }

    @GetMapping("/user/{user_id}/review")
    public ResponseEntity<List<ReviewByUserDto>> getAllReviewByUser(@PathVariable Long user_id) {
        System.out.println("[CUSTOM LOG "+ new LogDateTime().getDate() + "]     내가 쓴 리뷰 전체 조회");
        return ResponseEntity.ok(reviewService.getAllReviewByUser(user_id));
    }

    @DeleteMapping("/academy/{academy_id}/review/{review_id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long academy_id, @PathVariable Long review_id) {
        if (reviewService.deleteReview(academy_id, review_id)) {
            System.out.println("[CUSTOM LOG "+ new LogDateTime().getDate() + "]     리뷰 삭제");
            return ResponseEntity
                    .status(200)
                    .body("리뷰 삭제 완료");
        }
        else {
            System.out.println("[CUSTOM LOG "+ new LogDateTime().getDate() + "]     이미 리뷰 삭제됨");
            return ResponseEntity
                    .status(409)
                    .body("이미 삭제된 리뷰입니다");
        }
    }
}
