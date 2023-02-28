package com.example.hakone.HakOne.controller;

import com.example.hakone.HakOne.config.auth.AuthService;
import com.example.hakone.HakOne.dto.GoogleLoginReqDto;
import com.example.hakone.HakOne.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
        return ResponseEntity.ok(authService.googleLogin(authCode));
    }
}
