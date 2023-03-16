package com.example.hakone.HakOne.dto;

import com.example.hakone.HakOne.domain.academy.Academy;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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
    // 별점, 리뷰 구현 예정
    int review_count;
}
