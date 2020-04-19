package com.oauthserver.config.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * @author 이승환
 * @since 2020-04-13
 *
 * https://spring.io/blog/2019/11/14/spring-security-oauth-2-0-roadmap-update
 *
 * spring-security-oauth2의 지원을 종료하고 리소스 서버와 클라이언트를
 * spring-security.5.2.x 버전 부터 통합하여 지원하기로 나와 있는데 그 중 인증 서버에 관련된 프레임워크는 지원을 끊기로 결정.
 *
 * 인증서버에 대한 구현은 spring-security-oauth2 2.3.x 버전은 2020년 3월 까지는 지원할 예정, 2021년 까지 2.4.x 버전은 지속해서 지원할 예정.
 *
 * keycloak 과 같은 오픈 소스를 활용 할것.
 */
@Configuration
@EnableAuthorizationServer // 권한서버 활성화
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private static AuthenticationManager authenticationManager;

    public void AuthorizationServerConfigurer(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * OAuth2 인증서버 자체의 보안 정보를 설정하는 부분
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 토큰유효성(/token/check_token) 접근을 위해 설정
        security.checkTokenAccess("permitAll()");
    }

    /**
     * Client 에 대한 정보를 설정하는 부분
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // @formatter:off
        clients.inMemory()
               .withClient("TestClientId")                       // 클라이언트 아이디
               .secret("{noop}TestSecret")                              // 클라이언트 시크릿(아직 암호화 하지않는 관계로 {noop}처리)
               .redirectUris("http://localhost:8081/oauth2/callback")   // 인증 완료 후 이동할 클라이언트 웹 페이지 주소로 code 값 전송
               .authorizedGrantTypes("authorization_code", "password", "refresh_token")     // 엑세스 토크 발급 가능한 인증 타입
               //.authorities("ROLE_USER")                              // 클라이언트에 부여된 권한
               .scopes("read", "write")                                 // 인증 후 얻은 accessToken으로 접근할 수 있는 리소스의 범위
               //.autoApprove(true)                                     // OAuth Approval 승인창 무시하고 모든권한 승인한다.
               .accessTokenValiditySeconds(60 * 60 * 4)                 // 발급된 accessToken의 유효시간(초)
               .refreshTokenValiditySeconds(60 * 60 * 24 * 120);        // 발급된 refreshToken의 유효시간(초)
        // @formatter:on
    }

    /**
     * OAuth2 서버가 작동하기 위한 Endpoint에 대한 정보를 설정
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }

}
