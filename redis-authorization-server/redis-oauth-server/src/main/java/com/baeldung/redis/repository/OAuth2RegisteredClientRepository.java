package com.baeldung.redis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.redis.entity.OAuth2RegisteredClient;

@Repository
public interface OAuth2RegisteredClientRepository extends CrudRepository<OAuth2RegisteredClient, String> {

    OAuth2RegisteredClient findByClientId(String clientId);

}
