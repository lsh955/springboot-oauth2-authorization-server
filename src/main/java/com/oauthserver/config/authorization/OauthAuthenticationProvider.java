package com.oauthserver.config.authorization;

import com.oauthserver.vo.TestUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author 이승환
 * @since 2020/04/19
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class OauthAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        log.info("authentication.username = " + username);
        log.info("authentication.password = " + password);

        // 테스트 유저 호출(만약 DB에 연동해서 불러온다면 대체해도 된다)
        TestUser testUser = new TestUser();
        if (password.equals(testUser.getPassword()) == false) {
            throw new BadCredentialsException(username);
        }
        return new UsernamePasswordAuthenticationToken(username, password, testUser.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
