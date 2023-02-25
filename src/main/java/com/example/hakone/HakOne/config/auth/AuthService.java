package com.example.hakone.HakOne.config.auth;

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
    private final JwtTokenUtil jwtTokenUtil;

    private GoogleUserInfoDto getGoogleUserInfoDto(String authCode) throws JsonProcessingException {
        ResponseEntity<String> accessTokenResponse = googleService.requestAccessToken(authCode);
        GoogleOAuthTokenDto oAuthToken = googleService.getAccessToken(accessTokenResponse);
        ResponseEntity<String> userInfoResponse = googleService.requestUserInfo(oAuthToken);
        GoogleUserInfoDto googleUser = googleService.getUserInfo(userInfoResponse);
        return googleUser;
    }

    public TokenDto googleLogin(String authCode) throws IOException {
        String jwt;
        GoogleUserInfoDto googleUser = getGoogleUserInfoDto(authCode);

        if (userRepository.findByEmail(googleUser.getEmail()).orElse(null) == null) {
            User user = User.builder()
                    .name(googleUser.getName())
                    .email(googleUser.getEmail())
                    .profile_pic(googleUser.getProfile_pic())
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
                .profile_pic(googleUser.getProfile_pic())
                .build();
        return tokenDto;
    }
//
//
//
//
//    // 구글 로그인
//    @Transactional
//    public TokenDto googleLogin(String accessToken) {
//        String jwt;
//        GoogleOAuth2UserInfoDto googleOAuth2UserInfoDto = googleService.getUserInfoByAccessToken(accessToken);
//
//        if (userRepository.findByEmail(googleOAuth2UserInfoDto.getEmail()).orElse(null) == null) {
//            User user = User.builder()
//                    .name(googleOAuth2UserInfoDto.getName())
//                    .email(googleOAuth2UserInfoDto.getEmail())
//                    .profile_pic(googleOAuth2UserInfoDto.getProfile_pic())
//                    .build();
//
//            userRepository.save(user);
//        }
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername(googleOAuth2UserInfoDto.getEmail());
//
//        jwt = jwtTokenUtil.generateToken(userDetails);
//        return new TokenDto(jwt, googleOAuth2UserInfoDto.getEmail());
//    }
//
//    // (Receive authCode via HTTPS POST)
//    public void authResponseAPI(String authCode) {
//
//    }
// -------------------------------
//        if (request.getHeader("X-Requested-With") == null) {
//            // Without the `X-Requested-With` header, this request could be forged. Aborts.
//        }
//        // Set path to the Web application client_secret_*.json file you downloaded from the
//        // Google API Console: https://console.cloud.google.com/apis/credentials
//        // You can also find your Web application client ID and client secret from the
//        // console and specify them directly when you create the GoogleAuthorizationCodeTokenRequest
//        // object.
//        String CLIENT_SECRET_FILE = "src/main/resources/client_secret_697762565420-50p4qpmpptl3htmccoufhrq6sasf12iq.apps.googleusercontent.com.json";
//
//        // Exchange auth code for access token
//        GoogleClientSecrets clientSecrets =
//                GoogleClientSecrets.load(
//                        JacksonFactory.getDefaultInstance(), new FileReader(CLIENT_SECRET_FILE));
//        GoogleTokenResponse tokenResponse =
//                new GoogleAuthorizationCodeTokenRequest(
//                        new NetHttpTransport(),
//                        JacksonFactory.getDefaultInstance(),
//                        "https://oauth2.googleapis.com/token",
//                        clientSecrets.getDetails().getClientId(),
//                        clientSecrets.getDetails().getClientSecret(),
//                        authCode,
//                        "")  // Specify the same redirect URI that you use with your web
//                        // app. If you don't have a web version of your app, you can
//                        // specify an empty string.
//                        .execute();
//
//        String accessToken = tokenResponse.getAccessToken();
//
//        // Use access token to call API
//        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
//        Drive drive =
//                new Drive.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
//                        .setApplicationName("Auth Code Exchange Demo")
//                        .build();
//        File file = drive.files().get("appfolder").execute();
//
//        // Get profile info from ID token
//        GoogleIdToken idToken = tokenResponse.parseIdToken();
//        GoogleIdToken.Payload payload = idToken.getPayload();
//        String userId = payload.getSubject();  // Use this value as a key to identify a user.
//        String email = payload.getEmail();
//        boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
//        String name = (String) payload.get("name");
//        String pictureUrl = (String) payload.get("picture");
//        String locale = (String) payload.get("locale");
//        String familyName = (String) payload.get("family_name");
//        String givenName = (String) payload.get("given_name");
//
//        return accessToken;


}
