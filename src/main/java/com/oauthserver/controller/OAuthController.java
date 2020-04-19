package com.oauthserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.oauthserver.dto.OAuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/**
 * @author 이승환
 * @since 2020-04-14
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth2")
public class OAuthController {

    private final Gson gson;
    private final RestTemplate restTemplate;

    @GetMapping("/oauth2/callback")
    public String callback(@RequestParam String code) throws JsonProcessingException {
        log.info("code = " + code);
        OAuthToken token = callbackSocial(code);
        log.info("getToken() = " + token);
        return token.toString();
    }

    @GetMapping(value = "/callback")
    public OAuthToken callbackSocial(@RequestParam String code) {

        String credentials = "TestClientId:TestSecret";
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

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
            return gson.fromJson(response.getBody(), OAuthToken.class);
        }
        return null;
    }

}
