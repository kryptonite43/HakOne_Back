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


