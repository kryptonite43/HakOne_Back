package com.example.hakone.HakOne.domain.UserAcademy;

import com.example.hakone.HakOne.domain.academy.Academy;
import com.example.hakone.HakOne.domain.user.Role;
import com.example.hakone.HakOne.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="Star")
public class UserAcademy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User member;

    @ManyToOne
    @JoinColumn(name = "academy_id")
    private Academy academy;

    @Builder
    public UserAcademy(User member, Academy academy) {
        this.member = member;
        this.academy = academy;
    }


}
