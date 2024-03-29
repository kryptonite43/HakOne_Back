package com.example.hakone.HakOne.controller;

import com.example.hakone.HakOne.Service.StarService;
import com.example.hakone.HakOne.domain.LogDateTime;
import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.dto.AllAcademyResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StarApiController {
    private final StarService starService;

    @PostMapping("/user/{user_id}/star/{academy_id}")
    public ResponseEntity<String> addAcademyAtStarList(@PathVariable Long user_id, @PathVariable Long academy_id) {
        if (starService.addStar(user_id, academy_id)) {
            System.out.println("[CUSTOM LOG "+ new LogDateTime().getDate() + "]     관심 학원 등록");
            return ResponseEntity
                    .status(200)
                    .body("관심 학원 등록 완료");
        }
        else {
            System.out.println("[CUSTOM LOG "+ new LogDateTime().getDate() + "]     이미 관심 등록됨");
            return ResponseEntity
                    .status(409)
                    .body("이미 관심 등록된 학원입니다");
        }
    }

    @DeleteMapping("/user/{user_id}/star/{academy_id}")
    public ResponseEntity<String> deleteAcademyAtStarList(@PathVariable Long user_id, @PathVariable Long academy_id) {
        if (starService.deleteStar(user_id, academy_id)) {
            System.out.println("[CUSTOM LOG "+ new LogDateTime().getDate() + "]     관심 학원 해제");
            return ResponseEntity
                    .status(200)
                    .body("관심 학원 해제 완료");
        }
        else {
            System.out.println("[CUSTOM LOG "+ new LogDateTime().getDate() + "]     이미 관심 해제됨");
            return ResponseEntity
                    .status(409)
                    .body("이미 관심 해제된 학원입니다");
        }
    }

    @GetMapping("/user/{user_id}/star")
    public ResponseEntity<List<AllAcademyResDto>> getUsersStarList(@PathVariable Long user_id) {
        List<Academy> usersStarList = starService.findAllByMember_Id(user_id);
        List<AllAcademyResDto> usersStarListResponse = starService.findAll(user_id, usersStarList);
        System.out.println("[CUSTOM LOG "+ new LogDateTime().getDate() + "]     관심 학원 전체 조회");
        return ResponseEntity.ok(usersStarListResponse);
    }
}
