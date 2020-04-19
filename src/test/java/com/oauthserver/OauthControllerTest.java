package com.oauthserver;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author 이승환
 * @since 2020/04/19
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class OauthControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("oauth token 테스트")
    void oauthTokenTest() throws Exception {
        // given
        String clientId = "TestClientId";
        String secret = "TestSecret";
        MultiValueMap params = new LinkedMultiValueMap();
        params.add("grant_type", "password");
        params.add("username", "user");
        params.add("password", "pass");

        // when
        MvcResult result = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(clientId, secret))
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andReturn();

        log.info("result = " + result);
        String contentAsString = result.getResponse().getContentAsString();
        log.info("body = " + contentAsString);

        // then
        Assertions.assertThat(contentAsString).contains("access_token").contains("refresh_token");
    }
}
