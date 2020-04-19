package com.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author 이승환
 * @since 2020/04/13
 * <p>
 * 공통 환경 세팅
 */
@Configuration
public class AppCommon {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}