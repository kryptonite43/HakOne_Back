package com.example.hakone.HakOne.dto;
import com.example.hakone.HakOne.domain.user.User;
import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    private String token;
    private String name;
    private String email;
    private String profile_pic;
}