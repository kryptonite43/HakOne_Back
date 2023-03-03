package com.example.hakone.HakOne.domain.academy;

import com.example.hakone.HakOne.domain.classroom.Classroom;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="Academy")
public class Academy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "학원명", nullable = false)
    private String academyName;
    @Column(name = "지역(구)")
    private String region;
    @Column(name = "학원주소")
    private String address;
    @Column(name = "전화번호")
    private String tel;
    @Column(name = "강사수")
    private int teacher;

    @Column(name = "학년-초", nullable = false)
    private boolean elem_grade;
    @Column(name = "학년-중", nullable = false)
    private boolean mid_grade;
    @Column(name = "학년-고", nullable = false)
    private boolean high_grade;
    @Column(name = "학년-기타", nullable = false)
    private boolean else_grade;

    @Column(name = "과목-국어", nullable = false)
    private boolean kor_class;
    @Column(name = "과목-영어", nullable = false)
    private boolean eng_class;
    @Column(name = "과목-수학", nullable = false)
    private boolean math_class;
    @Column(name = "과목-사회", nullable = false)
    private boolean soc_class;
    @Column(name = "과목-과학", nullable = false)
    private boolean sci_class;
    @Column(name = "과목-외국어", nullable = false)
    private boolean for_class;
    @Column(name = "과목-논술", nullable = false)
    private boolean essay_class;
    @Column(name = "과목-예능", nullable = false)
    private boolean art_class;
    @Column(name = "과목-기타", nullable = false)
    private boolean else_class;
    @OneToMany(mappedBy = "academy", cascade = CascadeType.ALL)
    private List<Classroom> classroomList;
    private float avg_score;
    private int avg_tuition;

    @Builder
    public Academy(String academyName, String region, String address, String tel, int teacher) {
        this.academyName = academyName;
        this.region = region;
        this.address = address;
        this.tel = tel;
        this.teacher = teacher;
        this.avg_score = 0;
        this.avg_tuition = 0;
    }
}


