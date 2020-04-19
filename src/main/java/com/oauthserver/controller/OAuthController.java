package com.oauthserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oauthserver.dto.OAuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;


/**
 * @author 이승환
 * @since 2020-04-14
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @GetMapping("/oauth2/callback")
    public OAuthToken callback(@RequestParam String code, HttpServletRequest request) throws Exception {

        log.info("code : " + code);
        OAuthToken token = getToken(code);
        log.info("getToken() : " + token);

        return token;

    }

    /**
     * token을 호출하여 access_token 획득
     *
     * @param code
     * @return
     * @throws JsonProcessingException
     */
    public OAuthToken getToken(String code) throws JsonProcessingException {

        String credentials = "TestClientId:TestSecret";
        String encodedCredentials = new String(Base64.encode(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Basic " + encodedCredentials);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", "http://localhost:8081/oauth2/callback");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8081/oauth/token", request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("response.getBody() : " + response.getBody());
            OAuthToken oauthTokenDto = objectMapper.readValue(response.getBody(), OAuthToken.class);
            return oauthTokenDto;
        }

        return null;

    }

}
