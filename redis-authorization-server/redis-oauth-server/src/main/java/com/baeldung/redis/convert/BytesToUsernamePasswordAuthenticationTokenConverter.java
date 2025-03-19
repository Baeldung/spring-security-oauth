
package com.baeldung.redis.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.jackson2.SecurityJackson2Modules;

import com.fasterxml.jackson.databind.ObjectMapper;

@ReadingConverter
public class BytesToUsernamePasswordAuthenticationTokenConverter implements Converter<byte[], UsernamePasswordAuthenticationToken> {

    private final Jackson2JsonRedisSerializer<UsernamePasswordAuthenticationToken> serializer;

    public BytesToUsernamePasswordAuthenticationTokenConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModules(SecurityJackson2Modules.getModules(BytesToUsernamePasswordAuthenticationTokenConverter.class.getClassLoader()));
        this.serializer = new Jackson2JsonRedisSerializer<>(objectMapper, UsernamePasswordAuthenticationToken.class);
    }

    @Override
    public UsernamePasswordAuthenticationToken convert(byte[] value) {
        return this.serializer.deserialize(value);
    }

}
