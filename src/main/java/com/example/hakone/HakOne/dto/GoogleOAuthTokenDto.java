package com.example.hakone.HakOne.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class GoogleOAuthTokenDto {
    private String access_token;
    private Integer expires_in;
    private String scope;
    private String token_type;
    private String id_token;
}
