package com.example.hakone.HakOne.config.auth;

import com.example.hakone.HakOne.dto.GoogleOAuthTokenDto;
import com.example.hakone.HakOne.dto.GoogleUserInfoDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoogleService {
    private final String googleLoginUrl = "https://accounts.google.com";
    private final String GOOGLE_TOKEN_REQUEST_URL = "https://oauth2.googleapis.com/token";
    private final String GOOGLE_USERINFO_REQUEST_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${google.clientId}")
    private String googleClientId;
    @Value("${google.redirect}")
    private String googleRedirectUrl;
    @Value("${google.secret}")
    private String googleClientSecret;

    public ResponseEntity<String> requestAccessToken(String authCode) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        params.put("code", authCode);
        params.put("client_id", googleClientId);
        params.put("client_secret", googleClientSecret);
        params.put("redirect_uri", googleRedirectUrl);
        params.put("grant_type","authorization_code");

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_REQUEST_URL,params,String.class);

        if (responseEntity.getStatusCode()== HttpStatus.OK) {
            return responseEntity;
        }
        return null;
    }

    public GoogleOAuthTokenDto getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        System.out.println("response.getBody() = " + response.getBody());
        GoogleOAuthTokenDto googleOAuthTokenDto = objectMapper.readValue(response.getBody(), GoogleOAuthTokenDto.class);
        return googleOAuthTokenDto;
    }

    public ResponseEntity<String> requestUserInfo(GoogleOAuthTokenDto oAuthToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer " + oAuthToken.getAccess_token());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(GOOGLE_USERINFO_REQUEST_URL, HttpMethod.GET, request,String.class);
        System.out.println("response.getBody() = " + response.getBody());
        return response;
    }

    public GoogleUserInfoDto getUserInfo(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        GoogleUserInfoDto googleUserInfoDto = objectMapper.readValue(response.getBody(), GoogleUserInfoDto.class);
        return googleUserInfoDto;
    }
//    public GoogleOAuth2UserInfoDto getUserInfoByAccessToken(String accessToken) {
//        final String userInfoUri = "https://www.googleapis.com/oauth2/v3/userinfo";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + accessToken);
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("access_token", accessToken);
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
//
//        try {
//            ResponseEntity<String> response = restTemplate.postForEntity(userInfoUri, request, String.class);
//
//            GoogleOAuth2UserInfoDto googleOAuth2UserInfoDto = objectMapper.readValue(response.getBody(), GoogleOAuth2UserInfoDto.class);
//
//            return googleOAuth2UserInfoDto;
//
//        } catch (RestClientException | JsonProcessingException e) {
//            log.error(e.getMessage());
//
//            throw new AccessTokenException();
//        }
//    }
}
