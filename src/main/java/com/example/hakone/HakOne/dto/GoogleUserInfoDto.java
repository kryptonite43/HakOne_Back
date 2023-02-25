package com.example.hakone.HakOne.dto;
import com.example.hakone.HakOne.domain.user.User;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class GoogleUserInfoDto {
    private String id;
    private String email;
    private Boolean verified_email;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String locale;

    public GoogleUserInfoDto(String toString, String email, boolean b, String name, String s, String s1, String profile_pic, String s2) {
    }
}