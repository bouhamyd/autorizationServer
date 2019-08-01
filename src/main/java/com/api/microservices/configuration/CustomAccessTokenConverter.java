package com.api.microservices.configuration;

import com.api.microservices.domaine.CustomUser;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;


public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {

    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.putAll(super.convertAccessToken(token, authentication));
        return response;
    }
}
