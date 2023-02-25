package com.example.hakone.HakOne.dto;
import com.example.hakone.HakOne.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class GoogleUserInfoDto extends User {
    private String name;
    private String email;
    private String profile_pic;
}