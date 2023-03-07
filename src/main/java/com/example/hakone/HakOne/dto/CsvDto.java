package com.example.hakone.HakOne.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CsvDto {
    private String academyName;
    private String region;
    private String address;
    private String tel;
    private int month_tuition; // 1개월 비용 : 반별
    private int teacher;

    private boolean elem_grade;
    private boolean mid_grade;
    private boolean high_grade;
    private boolean else_grade;

    private boolean kor_class;
    private boolean eng_class;
    private boolean math_class;
    private boolean soc_class;
    private boolean sci_class;
    private boolean for_class;
    private boolean essay_class;
    private boolean art_class;
    private boolean else_class;

    private String className;

    public List<Boolean> subjectToList() {
        List<Boolean> subjectList = new ArrayList<>();
        subjectList.add(kor_class);
        subjectList.add(eng_class);
        subjectList.add(math_class);
        subjectList.add(soc_class);
        subjectList.add(sci_class);
        subjectList.add(for_class);
        subjectList.add(essay_class);
        subjectList.add(art_class);
        subjectList.add(else_class);

        return subjectList;
    }
}
