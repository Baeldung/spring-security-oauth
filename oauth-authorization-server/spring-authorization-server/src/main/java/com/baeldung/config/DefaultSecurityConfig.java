package com.baeldung.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenCustomizer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class DefaultSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
          authorizeRequests.anyRequest().authenticated()
        )
          .formLogin(withDefaults());
        return http.build();
    }

    @Bean
    UserDetailsService users() {
        UserDetails user = User.withDefaultPasswordEncoder()
          .username("admin")
          .password("password")
          .roles("USER")
          .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    @Profile("basic-claim")
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
        return (context) -> {
          if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
            context.getClaims().claims((claims) -> {
              claims.put("claim-1", "value-1");
              claims.put("claim-2", "value-2");
            });
          }
        };
    }

    @Bean
    @Profile("authority-claim")
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(@Qualifier("users") UserDetailsService userDetailsService) {
        return (context) -> {
          UserDetails userDetails = userDetailsService.loadUserByUsername(context.getPrincipal().getName());
          Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
          context.getClaims().claims(claims ->
            claims.put("authorities", authorities.stream().map(authority -> authority.getAuthority()).collect(Collectors.toList())));
        };
    }

}
