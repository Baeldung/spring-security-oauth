package com.baeldung.redis.config;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import com.baeldung.redis.convert.BytesToClaimsHolderConverter;
import com.baeldung.redis.convert.BytesToOAuth2AuthorizationRequestConverter;
import com.baeldung.redis.convert.BytesToUsernamePasswordAuthenticationTokenConverter;
import com.baeldung.redis.convert.ClaimsHolderToBytesConverter;
import com.baeldung.redis.convert.OAuth2AuthorizationRequestToBytesConverter;
import com.baeldung.redis.convert.UsernamePasswordAuthenticationTokenToBytesConverter;
import com.baeldung.redis.repository.OAuth2AuthorizationGrantAuthorizationRepository;
import com.baeldung.redis.repository.OAuth2RegisteredClientRepository;
import com.baeldung.redis.repository.OAuth2UserConsentRepository;
import com.baeldung.redis.service.RedisOAuth2AuthorizationConsentService;
import com.baeldung.redis.service.RedisOAuth2AuthorizationService;
import com.baeldung.redis.service.RedisRegisteredClientRepository;

import jakarta.annotation.PreDestroy;

@EnableRedisRepositories("com.baeldung.redis.repository")
@Configuration(proxyBeanMethods = false)
public class RedisConfig {

    private static GenericContainer<?> redis;

    static {
        redis = new GenericContainer<>(DockerImageName.parse("redis:5.0.3-alpine")).withExposedPorts(6379);
        redis.start();
        System.setProperty("spring.redis.host", redis.getHost());
        System.setProperty("spring.redis.port", redis.getMappedPort(6379)
            .toString());
    }

    @PreDestroy
    public void preDestroy() throws IOException {
        redis.stop();
    }

    @Bean
    @Order(1)
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redis.getHost(), redis.getMappedPort(6379));
        //return new JedisConnectionFactory(config);
        return new LettuceConnectionFactory(config);
    }

    @Bean
    @Order(2)
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    @Order(3)
    public RedisCustomConversions redisCustomConversions() {
        return new RedisCustomConversions(Arrays.asList(new UsernamePasswordAuthenticationTokenToBytesConverter(),
            new BytesToUsernamePasswordAuthenticationTokenConverter(), new OAuth2AuthorizationRequestToBytesConverter(),
            new BytesToOAuth2AuthorizationRequestConverter(), new ClaimsHolderToBytesConverter(), new BytesToClaimsHolderConverter()));
    }

    @Bean
    @Order(4)
    public RedisRegisteredClientRepository registeredClientRepository(OAuth2RegisteredClientRepository registeredClientRepository) {
        RedisRegisteredClientRepository redisRegisteredClientRepository = new RedisRegisteredClientRepository(registeredClientRepository);
        //return new RedisRegisteredClientRepository(registeredClientRepository);
        redisRegisteredClientRepository.save(RegisteredClients.messagingClient());
        return redisRegisteredClientRepository;
    }

    @Bean
    @Order(5)
    public RedisOAuth2AuthorizationService authorizationService(RegisteredClientRepository registeredClientRepository,
        OAuth2AuthorizationGrantAuthorizationRepository authorizationGrantAuthorizationRepository) {
        return new RedisOAuth2AuthorizationService(registeredClientRepository, authorizationGrantAuthorizationRepository);
    }

    @Bean
    @Order(6)
    public RedisOAuth2AuthorizationConsentService authorizationConsentService(OAuth2UserConsentRepository userConsentRepository) {
        return new RedisOAuth2AuthorizationConsentService(userConsentRepository);
    }
}
