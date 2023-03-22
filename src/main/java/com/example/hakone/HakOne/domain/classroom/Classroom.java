package com.example.hakone.HakOne.domain.classroom;

import com.example.hakone.HakOne.domain.academy.Academy;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="Class")
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long id;

    @Column(name = "반명", nullable = false)
    private String name;
    @Column(name = "수강료")
    private int tuition;
    @Column(name = "전화번호")
    private String tel;
    @Column(name = "지역구")
    private String region;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academy_id")
    private Academy academy;

    @Builder
    public Classroom(String className, int tuition) {
        this.name = className;
        this.tuition = tuition;
        this.kor_class = isKor_class();
        this.eng_class = isEng_class();
        this.math_class = isMath_class();
        this.soc_class = isSoc_class();
        this.sci_class = isSci_class();
        this.for_class = isFor_class();
        this.essay_class = isEssay_class();
        this.art_class = isArt_class();
        this.else_class = isElse_class();
    }
}
