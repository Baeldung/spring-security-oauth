package com.baeldung.resource.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Objects;
@Slf4j
public class AudienceValidator implements OAuth2TokenValidator<Jwt> {
    AudienceValidator() {
    }

    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        log.info("Entering validate");
        OAuth2Error error = new OAuth2Error("invalid_token", "The required token has" +
                "preferred_name with non test domain", null);
        OAuth2TokenValidatorResult oAuth2TokenValidatorResultFailure = OAuth2TokenValidatorResult.failure(error);
        if (jwt.hasClaim("preferred_username")) {
            String userName = jwt.getClaimAsString("preferred_username");
            if (Objects.nonNull(userName) && userName.contains("test")) {
                log.info("OAuth2TokenValidator successful");
                return OAuth2TokenValidatorResult.success();
            } else {
                log.info("OAuth2TokenValidator failed");
                return oAuth2TokenValidatorResultFailure;
            }
        }
        return oAuth2TokenValidatorResultFailure;
    }
}
