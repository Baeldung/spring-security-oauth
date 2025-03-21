
package com.baeldung.redis.entity;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.security.core.GrantedAuthority;

@RedisHash("oauth2_authorization_consent")
public class OAuth2UserConsent {

    @Id
    private final String id;

    @Indexed
    private final String registeredClientId;

    @Indexed
    private final String principalName;

    private final Set<GrantedAuthority> authorities;

    public OAuth2UserConsent(String id, String registeredClientId, String principalName, Set<GrantedAuthority> authorities) {
        this.id = id;
        this.registeredClientId = registeredClientId;
        this.principalName = principalName;
        this.authorities = authorities;
    }

    public String getId() {
        return this.id;
    }

    public String getRegisteredClientId() {
        return this.registeredClientId;
    }

    public String getPrincipalName() {
        return this.principalName;
    }

    public Set<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
}
