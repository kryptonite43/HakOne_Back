package com.example.hakone.HakOne.config.auth;

import com.example.hakone.HakOne.domain.user.Role;
import com.example.hakone.HakOne.domain.user.User;
import com.example.hakone.HakOne.domain.user.UserRepository;
import com.example.hakone.HakOne.dto.GoogleOAuthTokenDto;
import com.example.hakone.HakOne.dto.GoogleUserInfoDto;
import com.example.hakone.HakOne.dto.TokenDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final GoogleService googleService;
    private final UserRepository userRepository;
    @Autowired
    private final CustomUserDetailsService userDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtTokenUtil jwtTokenUtil;

    private GoogleUserInfoDto getGoogleUserInfoDto(String authCode) throws JsonProcessingException {
        ResponseEntity<String> accessTokenResponse = googleService.requestAccessToken(authCode);
        System.out.println("------requestAccessToken Done------");
        GoogleOAuthTokenDto oAuthToken = googleService.getAccessToken(accessTokenResponse);
        System.out.println("------getAccessToken Done------");
        ResponseEntity<String> userInfoResponse = googleService.requestUserInfo(oAuthToken);
        System.out.println("------requestUserInfo Done------");
        GoogleUserInfoDto googleUser = googleService.getUserInfo(userInfoResponse);
        System.out.println("------getUserInfo Done------");
        return googleUser;
    }

    public TokenDto googleLogin(String authCode) throws IOException {
        String jwt;
        GoogleUserInfoDto googleUser = getGoogleUserInfoDto(authCode);
        System.out.println("-----------googleLogin - getGoogleUserInfoDTO done-----------");
        System.out.println("--------googleUser email: "+googleUser.getEmail());
        if (userRepository.findByEmail(googleUser.getEmail()).orElse(null) == null) {
            User user = User.builder()
                    .name(googleUser.getName())
                    .email(googleUser.getEmail())
                    .profile_pic(googleUser.getPicture())
                    .role(Role.USER)
                    .build();

            userRepository.save(user);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(googleUser.getEmail());

        jwt = jwtTokenUtil.generateToken(userDetails);
        System.out.println("token: "+jwt+", googleUserName: "+googleUser.getName());

        TokenDto tokenDto = TokenDto.builder()
                .token(jwt)
                .name(googleUser.getName())
                .email(googleUser.getEmail())
                .profile_pic(googleUser.getPicture())
                .build();
        return tokenDto;
    }

}
