package com.oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author 이승환
 * @since 2020/03/28
 */
@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.csrf().disable()                           // CSRF 비활성화
            .headers().frameOptions().sameOrigin()      // X-Frame-Options(클릭재킹) 활성화
            .and()
                .authorizeRequests()
                .antMatchers("/oauth/authorize").permitAll()
                .antMatchers("/oauth/**", "/oauth2/callback").permitAll()
            .and()
                .formLogin()
            .and()
                .httpBasic();
        // @formatter:on
    }

}
