package com.example.hakone.HakOne.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResDto {
    private String profile_pic;
    private String username;
    private float score;
    private String created_date;
    private String content;
}
