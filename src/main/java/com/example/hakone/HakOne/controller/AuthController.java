package com.example.hakone.HakOne.controller;

import com.example.hakone.HakOne.config.auth.AuthService;
import com.example.hakone.HakOne.dto.GoogleLoginReqDto;
import com.example.hakone.HakOne.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AuthController {
    AuthService authService;

    @PostMapping("/google/login")
    public ResponseEntity<TokenDto> callback(@RequestParam String authCode) throws IOException {
        System.out.println("authcode:"+authCode);


        return ResponseEntity.ok(authService.googleLogin(authCode));
    }
//    @ResponseBody
//    public void authResponse(@RequestBody String authCode) {
//        System.out.println("authCode: "+authCode);
//
//        return googleOAuth2UserService.authResponseAPI(authCode);
//    }

}
