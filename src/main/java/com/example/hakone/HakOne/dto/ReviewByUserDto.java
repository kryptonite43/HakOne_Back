package com.example.hakone.HakOne.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewByUserDto {
    private Long academy_id;
    private Long review_id;
    private String academy_name;
    private float avg_score;
    private int review_count;
    private String content;
    private float user_score;
    private String created_date;
}
