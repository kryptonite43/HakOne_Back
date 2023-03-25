package com.example.hakone.HakOne.controller;

import com.example.hakone.HakOne.Service.ReviewService;
import com.example.hakone.HakOne.domain.LogDateTime;
import com.example.hakone.HakOne.dto.CreateReviewReqDto;
import com.example.hakone.HakOne.dto.ReviewResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewApiController {
    private final ReviewService reviewService;

    @PostMapping("/user/{user_id}/academy/{academy_id}")
    public ResponseEntity<String> createReview(CreateReviewReqDto createReviewReqDto, @PathVariable Long user_id, @PathVariable Long academy_id) throws IOException {
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
    public ResponseEntity<List<ReviewResDto>> getAllReviewWithAcademy(@PathVariable Long academy_id) {
        System.out.println("[CUSTOM LOG "+ new LogDateTime().getDate() + "]     해당 학원 리뷰목록 조회");
        return ResponseEntity.ok(reviewService.getAllReviewByAcademy(academy_id));
    }
}
