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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final GoogleService googleService;
    private final UserRepository userRepository;
    @Autowired
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    private GoogleUserInfoDto getGoogleUserInfoDto(String authCode) throws JsonProcessingException {
        ResponseEntity<String> accessTokenResponse = googleService.requestAccessToken(authCode);
        GoogleOAuthTokenDto oAuthToken = googleService.getAccessToken(accessTokenResponse);
        ResponseEntity<String> userInfoResponse = googleService.requestUserInfo(oAuthToken);
        GoogleUserInfoDto googleUser = googleService.getUserInfo(userInfoResponse);
        System.out.println("------------getGoogleUserInfoDto() done: "+googleUser.getName());
        return googleUser;
    }

    public TokenDto googleLogin(String authCode) throws IOException {
        String jwt;
        GoogleUserInfoDto googleUser = getGoogleUserInfoDto(authCode);
        if (userRepository.findByEmail(googleUser.getEmail()).orElse(null) == null) {
            System.out.println("------------userRepository에 없음, 새로 만들기: "+googleUser.getEmail());
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
        System.out.println("googleUser email: "+googleUser.getEmail());
        System.out.println("token: "+jwt+", googleUserName: "+googleUser.getName());

        User currentUser = userRepository.findByEmail(googleUser.getEmail()).get();

//        TokenDto tokenDto = TokenDto.builder()
//                .token(jwt)
//                .name(googleUser.getName())
//                .email(googleUser.getEmail())
//                .profile_pic(googleUser.getPicture())
//                .build();
        TokenDto tokenDto = TokenDto.builder()
                .user_id(currentUser.getId())
                .token(jwt)
                .name(currentUser.getName())
                .email(currentUser.getEmail())
                .profile_pic(currentUser.getProfile_pic())
                .build();
        return tokenDto;
    }

}
