package com.example.hakone.HakOne.controller;

import com.example.hakone.HakOne.config.auth.AuthService;
import com.example.hakone.HakOne.domain.LogDateTime;
import com.example.hakone.HakOne.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/google")
    public ResponseEntity<TokenDto> callback(@RequestParam("authCode") String authCode) throws IOException {
        System.out.println("authcode:"+authCode);
        System.out.println("[CUSTOM LOG "+ new LogDateTime().getDate() + "]     로그인");
        return ResponseEntity.ok(authService.googleLogin(authCode));
    }
}
