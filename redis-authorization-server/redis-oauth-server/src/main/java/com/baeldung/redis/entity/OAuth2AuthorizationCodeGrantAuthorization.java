package com.baeldung.redis.entity;

import java.security.Principal;
import java.time.Instant;
import java.util.Set;

import org.springframework.data.redis.core.index.Indexed;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

public class OAuth2AuthorizationCodeGrantAuthorization extends OAuth2AuthorizationGrantAuthorization {

    private final Principal principal;

    private final OAuth2AuthorizationRequest authorizationRequest;

    private final AuthorizationCode authorizationCode;

    @Indexed
    private final String state;

    public OAuth2AuthorizationCodeGrantAuthorization(String id, String registeredClientId, String principalName, Set<String> authorizedScopes,
        AccessToken accessToken, RefreshToken refreshToken, Principal principal, OAuth2AuthorizationRequest authorizationRequest,
        AuthorizationCode authorizationCode, String state) {
        super(id, registeredClientId, principalName, authorizedScopes, accessToken, refreshToken);
        this.principal = principal;
        this.authorizationRequest = authorizationRequest;
        this.authorizationCode = authorizationCode;
        this.state = state;
    }

    public Principal getPrincipal() {
        return this.principal;
    }

    public OAuth2AuthorizationRequest getAuthorizationRequest() {
        return this.authorizationRequest;
    }

    public AuthorizationCode getAuthorizationCode() {
        return this.authorizationCode;
    }

    public String getState() {
        return this.state;
    }

    public static class AuthorizationCode extends AbstractToken {

        public AuthorizationCode(String tokenValue, Instant issuedAt, Instant expiresAt, boolean invalidated) {
            super(tokenValue, issuedAt, expiresAt, invalidated);
        }

    }

}
