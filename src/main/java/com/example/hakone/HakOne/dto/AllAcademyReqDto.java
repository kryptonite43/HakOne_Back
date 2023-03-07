package com.example.hakone.HakOne.dto;

import java.util.List;

public class AllAcademyReqDto {
    Long academyId;
    String academyName;
    String region;
    String tel;
    int avgTuition;
    List<Boolean> gradeList; // {초, 중, 고, 기타}
    List<Boolean> subjectList; // {국어, 영어, 수학, 사회, 과학, 외국어, 논술, 예능, 기타}
    Boolean star; // 사용자가 관심한 학원인지 여부
    // 별점, 리뷰 구현 예정
}
