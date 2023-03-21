package com.example.hakone.HakOne.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Getter
@Setter
@NoArgsConstructor
public class AllAcademyResDto {
    Long academyId;
    String academyName;
    String region;
    String tel;
    int avgTuition;
    List<Boolean> gradeList; // {초, 중, 고, 기타}
    List<Boolean> subjectList; // {국어, 영어, 수학, 사회, 과학, 외국어, 논술, 예능, 기타}
    Boolean star;
    float avg_score;
    int review_count;
}
