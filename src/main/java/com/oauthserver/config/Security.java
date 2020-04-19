package com.oauthserver.config;

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

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // 클라이언트 정보는 테스트를 위해 일단 더미 데이터를 세팅
//
//        // @formatter:off
//        auth.inMemoryAuthentication()
//            .withUser("user")
//            .password("{noop}pass") // {noop}은 암호화를 하지 않았을 경우 넣어주는 식별자
//            .roles("USER");
//        // @formatter:on
//    }


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
