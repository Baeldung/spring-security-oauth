
package com.baeldung.redis.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;

import com.fasterxml.jackson.databind.ObjectMapper;

@WritingConverter
public class OAuth2AuthorizationRequestToBytesConverter implements Converter<OAuth2AuthorizationRequest, byte[]> {

    private final Jackson2JsonRedisSerializer<OAuth2AuthorizationRequest> serializer;

    public OAuth2AuthorizationRequestToBytesConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModules(SecurityJackson2Modules.getModules(OAuth2AuthorizationRequestToBytesConverter.class.getClassLoader()));
        objectMapper.registerModules(new OAuth2AuthorizationServerJackson2Module());
        this.serializer = new Jackson2JsonRedisSerializer<>(objectMapper, OAuth2AuthorizationRequest.class);
    }

    @Override
    public byte[] convert(OAuth2AuthorizationRequest value) {
        return this.serializer.serialize(value);
    }

}
