package com.baeldung.redis.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;

import com.fasterxml.jackson.databind.ObjectMapper;

@ReadingConverter
public class BytesToOAuth2AuthorizationRequestConverter implements Converter<byte[], OAuth2AuthorizationRequest> {

    private final Jackson2JsonRedisSerializer<OAuth2AuthorizationRequest> serializer;

    public BytesToOAuth2AuthorizationRequestConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModules(SecurityJackson2Modules.getModules(BytesToOAuth2AuthorizationRequestConverter.class.getClassLoader()));
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
        this.serializer = new Jackson2JsonRedisSerializer<>(objectMapper, OAuth2AuthorizationRequest.class);
    }

    @Override
    public OAuth2AuthorizationRequest convert(byte[] value) {
        return this.serializer.deserialize(value);
    }

}
