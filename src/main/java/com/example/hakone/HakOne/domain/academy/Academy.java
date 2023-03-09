package com.example.hakone.HakOne.domain.academy;

import com.example.hakone.HakOne.domain.classroom.Classroom;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Academy")
public class Academy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "academy_id")
    private Long id;

    @Column(name = "학원명", nullable = false)
    private String academyName;
    @Column(name = "지역구")
    private String region;
    @Column(name = "학원주소")
    private String address;
    @Column(name = "전화번호")
    private String tel;
    @Column(name = "강사수")
    private int teacher;

    @Column(name = "학년_초", nullable = false)
    private boolean elem_grade;
    @Column(name = "학년_중", nullable = false)
    private boolean mid_grade;
    @Column(name = "학년_고", nullable = false)
    private boolean high_grade;
    @Column(name = "학년_기타", nullable = false)
    private boolean else_grade;

    @Column(name = "과목_국어", nullable = false)
    private boolean kor_class;
    @Column(name = "과목_영어", nullable = false)
    private boolean eng_class;
    @Column(name = "과목_수학", nullable = false)
    private boolean math_class;
    @Column(name = "과목_사회", nullable = false)
    private boolean soc_class;
    @Column(name = "과목_과학", nullable = false)
    private boolean sci_class;
    @Column(name = "과목_외국어", nullable = false)
    private boolean for_class;
    @Column(name = "과목_논술", nullable = false)
    private boolean essay_class;
    @Column(name = "과목_예능", nullable = false)
    private boolean art_class;
    @Column(name = "과목_기타", nullable = false)
    private boolean else_class;

    @OneToMany(mappedBy = "academy", cascade = CascadeType.ALL)
    private List<Classroom> classroomList;

    private int avg_score;
    private int avg_tuition;

    @Builder
    public Academy(String academyName, String region, String address, String tel, int teacher,
                   boolean elem_grade, boolean mid_grade, boolean high_grade, boolean else_grade,
                   boolean kor_class, boolean eng_class, boolean math_class, boolean soc_class,
                   boolean sci_class, boolean for_class, boolean essay_class, boolean art_class, boolean else_class) {
        this.academyName = academyName;
        this.region = region;
        this.address = address;
        this.tel = tel;
        this.teacher = teacher;
        this.avg_score = 0;
        this.avg_tuition = 0;

        this.elem_grade = elem_grade;
        this.mid_grade = mid_grade;
        this.high_grade = high_grade;
        this.else_grade = else_grade;

        this.kor_class = kor_class;
        this.eng_class = eng_class;
        this.math_class = math_class;
        this.soc_class = soc_class;
        this.sci_class = sci_class;
        this.for_class = for_class;
        this.essay_class = essay_class;
        this.art_class = art_class;
        this.else_class = else_class;
    }

    public void updateScoreAndTuition(int avg_score, int avg_tuition) {
        this.avg_score = avg_score;
        this.avg_tuition = avg_tuition;
    }
}


