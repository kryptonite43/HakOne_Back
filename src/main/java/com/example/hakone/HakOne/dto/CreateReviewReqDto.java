package com.example.hakone.HakOne.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CreateReviewReqDto {
    MultipartFile receipt;
    float score;
    String content;
}
