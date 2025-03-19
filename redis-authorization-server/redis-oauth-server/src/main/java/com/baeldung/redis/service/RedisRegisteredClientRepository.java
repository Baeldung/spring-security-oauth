package com.baeldung.redis.service;

import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.util.Assert;

import com.baeldung.redis.entity.OAuth2RegisteredClient;
import com.baeldung.redis.repository.OAuth2RegisteredClientRepository;

public class RedisRegisteredClientRepository implements RegisteredClientRepository {

    private final OAuth2RegisteredClientRepository registeredClientRepository;

    public RedisRegisteredClientRepository(OAuth2RegisteredClientRepository registeredClientRepository) {
        Assert.notNull(registeredClientRepository, "registeredClientRepository cannot be null");
        this.registeredClientRepository = registeredClientRepository;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        Assert.notNull(registeredClient, "registeredClient cannot be null");
        OAuth2RegisteredClient oauth2RegisteredClient = ModelMapper.convertOAuth2RegisteredClient(registeredClient);
        this.registeredClientRepository.save(oauth2RegisteredClient);
    }

    @Nullable
    @Override
    public RegisteredClient findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return this.registeredClientRepository.findById(id)
            .map(ModelMapper::convertRegisteredClient)
            .orElse(null);
    }

    @Nullable
    @Override
    public RegisteredClient findByClientId(String clientId) {
        Assert.hasText(clientId, "clientId cannot be empty");
        OAuth2RegisteredClient oauth2RegisteredClient = this.registeredClientRepository.findByClientId(clientId);
        return oauth2RegisteredClient != null ? ModelMapper.convertRegisteredClient(oauth2RegisteredClient) : null;
    }

}
