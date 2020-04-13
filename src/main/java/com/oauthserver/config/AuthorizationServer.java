package com.oauthserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * @author 이승환
 * @since 2020-04-13
 *
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {
    
    /**
     * OAuth2 인증서버 자체의 보안 정보를 설정하는 부분
     * @param security
     * @throws Exception
     */
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//
//        super.configure(security);
//    }
    
    /**
     * Client 에 대한 정보를 설정하는 부분
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        
        clients.inMemory()
                .withClient("testClientId")
                .secret("testSecret")
                .redirectUris("http://localhost:8081/oauth2/callback")  // 인증 완료 후 이동할 클라이언트 웹 페이지 주소로 code 값 전송
                .authorizedGrantTypes("authorization_code")
                .scopes("read", "write")            // 인증 후 얻은 accessToken으로 접근할 수 있는 리소스의 범위
                .accessTokenValiditySeconds(30000); // 발급된 accessToken의 유효시간(초)
    
    }
    
    /**
     * OAuth2 서버가 작동하기 위한 Endpoint에 대한 정보를 설정
     * @param endpoints
     * @throws Exception
     */
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//
//        super.configure(endpoints);
//    }
    
}
