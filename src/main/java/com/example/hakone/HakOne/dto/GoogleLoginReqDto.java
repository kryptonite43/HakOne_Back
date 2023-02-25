package com.example.hakone.HakOne.dto;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleLoginReqDto {

    private String authCode;

    @Builder
    public GoogleLoginReqDto(String authCode) {
        this.authCode = authCode;
    }
}


