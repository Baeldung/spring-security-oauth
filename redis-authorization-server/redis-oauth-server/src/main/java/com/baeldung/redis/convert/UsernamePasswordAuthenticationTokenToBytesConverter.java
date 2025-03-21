package com.baeldung.redis.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.jackson2.SecurityJackson2Modules;

import com.fasterxml.jackson.databind.ObjectMapper;

@WritingConverter
public class UsernamePasswordAuthenticationTokenToBytesConverter implements Converter<UsernamePasswordAuthenticationToken, byte[]> {

    private final Jackson2JsonRedisSerializer<UsernamePasswordAuthenticationToken> serializer;

    public UsernamePasswordAuthenticationTokenToBytesConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModules(SecurityJackson2Modules.getModules(BytesToUsernamePasswordAuthenticationTokenConverter.class.getClassLoader()));
        this.serializer = new Jackson2JsonRedisSerializer<>(objectMapper, UsernamePasswordAuthenticationToken.class);
    }

    @Override
    public byte[] convert(UsernamePasswordAuthenticationToken value) {
        return this.serializer.serialize(value);
    }

}
