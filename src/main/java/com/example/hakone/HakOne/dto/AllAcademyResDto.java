package com.example.hakone.HakOne.dto;

import com.example.hakone.HakOne.domain.academy.Academy;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AllAcademyResDto {
    Long academyId;
    String academyName;
    String region;
    String tel;
    int avgTuition;
     List<Boolean> gradeList; // {초, 중, 고, 기타}
    List<Boolean> subjectList; // {국어, 영어, 수학, 사회, 과학, 외국어, 논술, 예능, 기타}
    Boolean star; // 사용자가 관심한 학원인지 여부
    // 별점, 리뷰 구현 예정

    public AllAcademyResDto(Academy academy){
        this.academyId = academy.getId();
        this.academyName = academy.getAcademyName();
        this.region = academy.getRegion();
        this.tel = academy.getTel();
        this.avgTuition = academy.getAvg_tuition();

        List<Boolean> grades = new ArrayList<>();
        grades.add(academy.isElem_grade());
        grades.add(academy.isMid_grade());
        grades.add(academy.isHigh_grade());
        grades.add(academy.isElse_grade());
        this.gradeList = grades;

        List<Boolean> subjects = new ArrayList<>();
        subjects.add(academy.isKor_class());
        subjects.add(academy.isEng_class());
        subjects.add(academy.isMath_class());
        subjects.add(academy.isSoc_class());
        subjects.add(academy.isSci_class());
        subjects.add(academy.isFor_class());
        subjects.add(academy.isEssay_class());
        subjects.add(academy.isArt_class());
        subjects.add(academy.isElse_class());
        this.subjectList = subjects;

        this.star = false; // 일단 구현 안해둬서 초기값으로 설정
    }
}
