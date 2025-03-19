
package com.baeldung.redis.entity;

import java.security.Principal;
import java.time.Instant;
import java.util.Set;

import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

public class OidcAuthorizationCodeGrantAuthorization extends OAuth2AuthorizationCodeGrantAuthorization {

    private final IdToken idToken;

    public OidcAuthorizationCodeGrantAuthorization(String id, String registeredClientId, String principalName, Set<String> authorizedScopes,
        AccessToken accessToken, RefreshToken refreshToken, Principal principal, OAuth2AuthorizationRequest authorizationRequest,
        AuthorizationCode authorizationCode, String state, IdToken idToken) {
        super(id, registeredClientId, principalName, authorizedScopes, accessToken, refreshToken, principal, authorizationRequest, authorizationCode, state);
        this.idToken = idToken;
    }

    public IdToken getIdToken() {
        return this.idToken;
    }

    public static class IdToken extends AbstractToken {

        private final ClaimsHolder claims;

        public IdToken(String tokenValue, Instant issuedAt, Instant expiresAt, boolean invalidated, ClaimsHolder claims) {
            super(tokenValue, issuedAt, expiresAt, invalidated);
            this.claims = claims;
        }

        public ClaimsHolder getClaims() {
            return this.claims;
        }

    }

}
