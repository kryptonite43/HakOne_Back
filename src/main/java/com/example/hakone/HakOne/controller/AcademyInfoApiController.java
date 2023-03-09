package com.example.hakone.HakOne.controller;

import com.example.hakone.HakOne.Service.AcademyInfoService;
import com.example.hakone.HakOne.dto.AllAcademyResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AcademyInfoApiController {
    private final AcademyInfoService academyInfoService;

    @GetMapping("/academy")
    public ResponseEntity<List<AllAcademyResDto>> getAllAcademyList() {
        return ResponseEntity.ok(academyInfoService.findAllDesc());
    }


}
