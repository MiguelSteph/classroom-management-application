package com.classmanagement.authorizationserver.configurations;

import com.classmanagement.authorizationserver.configurations.authDto.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.web.cors.CorsConfiguration;

import java.security.KeyPair;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Import(AuthorizationServerEndpointsConfiguration.class)
@Configuration
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${key_file}")
    private String keyFile;

    @Value("${key_file_password}")
    private String keyFilePassword;

    @Value("${alias}")
    private String alias;

    @Value("${client_id}")
    private String clientId;

    @Value("${client_pwd}")
    private String clientPwd;

    @Value("${resource_id}")
    private String resourceId;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        Map<String, CorsConfiguration> corsConfigMap = new HashMap<>();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        corsConfigMap.put("/oauth/token", config);

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));

        endpoints.getFrameworkEndpointHandlerMapping()
                .setCorsConfigurations(corsConfigMap);
        endpoints.tokenStore(jwtTokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientId)
                .secret(passwordEncoder.encode(clientPwd))
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("read", "write")
                .resourceIds(resourceId);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security.tokenKeyAccess("permitAll()");
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource(keyFile), keyFilePassword.toCharArray());
        return keyStoreKeyFactory.getKeyPair(alias);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }

    private TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            if (authentication != null && authentication.getPrincipal() instanceof AuthUser) {
                AuthUser authUser = (AuthUser) authentication.getPrincipal();
                Map<String, Object> additionalInfo = new HashMap<>();
                additionalInfo.put("is-default-pwd", authUser.isDefaultPwd());
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            }
            return accessToken;
        };
    }
}
