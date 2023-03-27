package com.example.hakone.HakOne.dto;

import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.domain.classroom.Classroom;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Getter
@Setter
@NoArgsConstructor
public class SpecificAcademyResDto {
    private String academyName;
    private String address;
    private String tel;
    private int teacher;
    private List<Boolean> subjectList;
    private int avg_tuition;
    private HashMap<String, Integer> classList;

    private Boolean star;
    private float avg_score;
    private int review_count;

    public SpecificAcademyResDto(Academy academy) {
        this.academyName = academy.getAcademyName();
        this.address = academy.getAddress();
        this.tel = academy.getTel();
        this.teacher = academy.getTeacher();
        this.avg_tuition = academy.getAvg_tuition();

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

        List<Classroom> classroomList = academy.getClassroomList();
        Map<String, Integer> classes = classroomList.stream().collect(
                Collectors.toMap(Classroom::getName, Classroom::getTuition));
        this.classList = (HashMap<String, Integer>) classes;

        this.star = false;
        this.avg_score = academy.getAvg_score();
        this.review_count = academy.getReview_count();
    }
}
