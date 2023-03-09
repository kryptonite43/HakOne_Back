package com.example.hakone.HakOne.controller;

import com.example.hakone.HakOne.Service.AcademyInfoService;
import com.example.hakone.HakOne.dto.AllAcademyResDto;
import com.example.hakone.HakOne.dto.SpecificAcademyResDto;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AcademyInfoApiController {
    private final AcademyInfoService academyInfoService;

    @GetMapping("/academy")
    public ResponseEntity<StreamingResponseBody> getAllAcademyList() {
        List<AllAcademyResDto> allAcademyResDtoList = academyInfoService.findAllDesc();
        ObjectMapper objectMapper = new ObjectMapper();

        StreamingResponseBody streamingResponseBody = outputStream -> {
            JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(outputStream);
            jsonGenerator.writeStartArray();

            for (AllAcademyResDto academyResDto : allAcademyResDtoList) {
                jsonGenerator.writeObject(academyResDto);
            }

            jsonGenerator.writeEndArray();
            jsonGenerator.flush();
            jsonGenerator.close();

            outputStream.flush();
            outputStream.close();
        };

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(streamingResponseBody);
    }

    @GetMapping("/academy/{academy_id}")
    public ResponseEntity<SpecificAcademyResDto> getSpecificAcademyInfo(@PathVariable Long academy_id) {
        return ResponseEntity.ok(academyInfoService.findById(academy_id));
    }


}
