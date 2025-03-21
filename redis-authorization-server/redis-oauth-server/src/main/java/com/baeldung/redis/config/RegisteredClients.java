package com.baeldung.redis.config;

import java.util.UUID;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

public class RegisteredClients {

    public static RegisteredClient messagingClient() {
        return RegisteredClient.withId(UUID.randomUUID()
            .toString())
            .clientId("articles-client")
            .clientSecret("{noop}secret")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .authorizationGrantType(AuthorizationGrantType.DEVICE_CODE)
            .redirectUri("http://127.0.0.1:8085/authorized")
            .redirectUri("http://127.0.0.1:8085/login/oauth2/code/articles-client-oidc")
            .postLogoutRedirectUri("http://127.0.0.1:9000/login")
            .scope(OidcScopes.OPENID)
            .scope("articles.read")
            .clientSettings(ClientSettings.builder()
                .requireAuthorizationConsent(true)
                .build())
            .build();
    }

}
