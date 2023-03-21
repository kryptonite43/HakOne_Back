package com.example.hakone.HakOne.domain.review;

import com.example.hakone.HakOne.domain.BaseTimeEntity;
import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="Review")
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User member;
    @ManyToOne
    @JoinColumn(name = "academy_id")
    private Academy academy;

    @Column(nullable = false)
    private float score;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String receipt;


    @Builder
    public Review (User member, Academy academy, float score, String content, String receipt) {
        this.member = member;
        this.academy = academy;
        this.score = score;
        this.content = content;
        this.receipt = receipt;
    }


}
