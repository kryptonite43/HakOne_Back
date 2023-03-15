package com.example.hakone.HakOne.controller;

import com.example.hakone.HakOne.Service.StarService;
import com.example.hakone.HakOne.dto.SpecificAcademyResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StarApiController {
    private final StarService starService;

    @PostMapping("/user/{user_id}/star/{academy_id}")
    public ResponseEntity<String> addAcademyAtStarList(@PathVariable Long user_id, @PathVariable Long academy_id) {
        if (starService.addStar(user_id, academy_id)) {
            return ResponseEntity
                    .status(200)
                    .body("관심 학원 등록 완료");
        }
        else {
            return ResponseEntity
                    .status(409)
                    .body("이미 관심 등록된 학원입니다");
        }
    }

}
