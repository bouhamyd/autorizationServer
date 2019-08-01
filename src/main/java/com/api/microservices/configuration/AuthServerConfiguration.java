package com.api.microservices.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.security.KeyPair;


@Configuration
@EnableAuthorizationServer
public class AuthServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private static final String CERT_ALIAS = "bblmsacert";

    private final AuthenticationManager authenticationManager;

    private final CertProperties certProperties;

    private final OAuth2ClientProperties oAuth2ClientProperties;

    private final TokenProperties tokenProperties;

    private final DataSource dataSourceProperties;

    private final ResourceLoader resourceLoader;

    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthServerConfiguration(AuthenticationManager authenticationManager,
                                   CertProperties certProperties,
                                   OAuth2ClientProperties oAuth2ClientProperties,
                                   TokenProperties tokenProperties,
                                   DataSource dataSourceProperties,
                                   ResourceLoader resourceLoader,
                                   UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.certProperties = certProperties;
        this.oAuth2ClientProperties = oAuth2ClientProperties;
        this.tokenProperties = tokenProperties;
        this.dataSourceProperties = dataSourceProperties;
        this.resourceLoader = resourceLoader;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        Resource cert = resourceLoader.getResource(certProperties.getFilePath());
        KeyPair keyPair = new KeyStoreKeyFactory(cert, certProperties.getPassword().toCharArray()).getKeyPair(CERT_ALIAS);
        jwtAccessTokenConverter.setKeyPair(keyPair);
        jwtAccessTokenConverter.setAccessTokenConverter(new CustomAccessTokenConverter());
        return jwtAccessTokenConverter;
    }

/*
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

*/
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(this.dataSourceProperties);
    }
/*
   @Bean
    public DataSource dataSource() {
        DriverManagerDataSource  dataSource =  new DriverManagerDataSource();
        dataSource.setDriverClassName(dataSourceProperties.getDrive());
        dataSource.setUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        return dataSource;
    }
    */

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore())
                .accessTokenConverter(jwtAccessTokenConverter())
                .authenticationManager(this.authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                //.jdbc(dataSource())
                .inMemory()
                .withClient(oAuth2ClientProperties.getClientId())
                .secret(oAuth2ClientProperties.getClientSecret())
                .authorizedGrantTypes("authorization_code", "implicit", "password", "refresh_token")
                .accessTokenValiditySeconds(tokenProperties.getAccessValiditySeconds())
                .refreshTokenValiditySeconds(tokenProperties.getRefreshValidityMinutes() * 60)
                .scopes("openid")
                .autoApprove(true);
    }
}
