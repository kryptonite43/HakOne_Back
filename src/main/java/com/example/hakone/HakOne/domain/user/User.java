package com.example.hakone.HakOne.domain.user;

import com.example.hakone.HakOne.domain.UserAcademy.UserAcademy;
import com.example.hakone.HakOne.domain.review.Review;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@Entity
@Table(name="Member")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String profile_pic;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<UserAcademy> userAcademies = new ArrayList<>(); // 학원 정보 가져오기 위함

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;
    @Builder
    public User(String name, String email, String profile_pic, Role role) {
        this.name = name;
        this.email = email;
        this.profile_pic = profile_pic;
        this.role = Role.USER;
    }

    public User update(String name, String profile_pic) {
        this.name = name;
        this.profile_pic = profile_pic;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
