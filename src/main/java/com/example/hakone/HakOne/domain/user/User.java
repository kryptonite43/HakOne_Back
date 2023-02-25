package com.example.hakone.HakOne.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String profile_pic;

    @Builder
    public User(String name, String email, String profile_pic) {
        this.name = name;
        this.email = email;
        this.profile_pic = profile_pic;
    }

    public User update(String name, String profile_pic) {
        this.name = name;
        this.profile_pic = profile_pic;

        return this;
    }
}
