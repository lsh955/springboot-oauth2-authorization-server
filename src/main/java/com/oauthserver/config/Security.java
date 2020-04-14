package com.oauthserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 이승환
 * @since 2020/03/28
 */
@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {
    
    @Bean
    public PasswordEncoder noOpPasswordEncoder() {
        // 더미 데이터를 세팅으로 인해 패스워드 인코딩은 아직 사용 안할거임.
        return NoOpPasswordEncoder.getInstance();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 클라이언트 정보는 테스트를 위해 일단 더미 데이터를 세팅
        
        // @formatter:off
        auth.inMemoryAuthentication()
            .withUser("user")
            .password("{noop}pass") // {noop}은 암호화를 하지 않았을 경우 넣어주는 식별자
            .roles("USER");
        // @formatter:on
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http    .csrf().disable()                           // CSRF 비활성화
                .headers().frameOptions().sameOrigin()      // X-Frame-Options(클릭재킹) 활성화
            .and()
                .authorizeRequests().antMatchers("/**", "/oauth/**", "/oauth2/callback", "/h2-console/*").permitAll()
            .and()
                .formLogin()
            .and()
                .httpBasic();
        // @formatter:on
    }
    
}
