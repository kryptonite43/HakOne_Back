package com.example.hakone.HakOne.domain.academy;

import com.example.hakone.HakOne.domain.user.Role;
import lombok.*;

import javax.persistence.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="Academies")
public class Academy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String academy_name;
    private String region;
    private String address;
    private String tel;
    private int teacher;

    @Column(nullable = false)
    private boolean elem_grade;
    @Column(nullable = false)
    private boolean mid_grade;
    @Column(nullable = false)
    private boolean high_grade;
    @Column(nullable = false)
    private boolean else_grade;

    @Column(nullable = false)
    private boolean kor_class;
    @Column(nullable = false)
    private boolean eng_class;
    @Column(nullable = false)
    private boolean math_class;
    @Column(nullable = false)
    private boolean soc_class;
    @Column(nullable = false)
    private boolean sci_class;
    @Column(nullable = false)
    private boolean for_class;
    @Column(nullable = false)
    private boolean essay_class;
    @Column(nullable = false)
    private boolean art_class;
    @Column(nullable = false)
    private boolean else_class;

    private float avg_score;
    private int avg_tuition;

    @Builder
    public Academy(String academy_name, String region, String address, String tel, int teacher) {
        this.academy_name = academy_name;
        this.region = region;
        this.address = address;
        this.tel = tel;
        this.teacher = teacher;
        this.avg_score = 0;
        this.avg_tuition = 0;
    }
}


