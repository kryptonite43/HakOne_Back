package com.example.hakone.HakOne.domain.classroom;

import com.example.hakone.HakOne.domain.academy.Academy;
import lombok.*;

import javax.persistence.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="Class")
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "반명", nullable = false)
    private String name;
    @Column(name = "수강료")
    private int tuition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academy_id")
    private Academy academy;

    @Builder
    public Classroom(String className, int tuition) {
        this.name = className;
        this.tuition = tuition;
    }
}
