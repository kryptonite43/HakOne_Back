package com.example.hakone.HakOne.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserResDto {
    String message;
    String email;
}
