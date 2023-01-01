package com.baeldung.resource;

import com.baeldung.resource.persistence.model.Foo;
import com.baeldung.resource.service.IFooService;
import com.baeldung.resource.spring.SecurityConfig;
import com.baeldung.resource.web.controller.FooController;
import com.c4_soft.springaddons.security.oauth2.test.annotations.OpenIdClaims;
import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockJwtAuth;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


/**
 * @WithMockJwtAuth(authorities =  "ROLE_AUTHORIZED_PERSONNEL", claims =  @OpenIdClaims(sub = "Ch4mpy",  preferredUsername = "Tonton Pirate"))
 */
@WebMvcTest(FooController.class) // Use WebFluxTest or WebMvcTest
//@AutoConfigureAddonsWebSecurity
//@SpringBootTest
//@WithMockJwtAuth
@Import({ SecurityConfig.class }) // Import your web-security configuration
@AutoConfigureMockMvc
public class FooControllerTest {
    // Mock controller injected dependencies
    @MockBean
    private IFooService mockFooService;

    /*@Autowired
    MockMvcSupport api;*/

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtDecoder jwtDecoder;

    @BeforeEach
    public void setUp() {
        /*when(messageService.greet(any())).thenAnswer(invocation -> {
            final JwtAuthenticationToken auth = invocation.getArgument(0, JwtAuthenticationToken.class);
            return String.format("Hello %s! You are granted with %s.", auth.getName(), auth.getAuthorities());
        });
        when(messageService.getSecret()).thenReturn("Secret message");*/
    }

    // TODO - Update Controller tests to ensure verification of AudienceValidator as part of JWTDecoder
    @Nested
    @DisplayName("Find list of all Foos")
    class FindAllFoos {

        //@Test
        //@DisplayName("findAllWithSecurityMockMvcRequestPostProcessors_Successful")
        //@WithMockJwtAuth(authorities = "offline_access", claims = @OpenIdClaims(preferredUsername = "john@test.com"))
        /*void findAllWithSecurityMockMvcRequestPostProcessors_Successful() throws Exception {
            Foo abcFoo = new Foo("abc");
            abcFoo.setId(123L);
            List<Foo> fooList = new ArrayList<>();
            fooList.add(abcFoo);
            Mockito
                    .when(mockFooService.findAll())
                    .thenReturn(fooList);

            mockMvc
                .perform(MockMvcRequestBuilders
                            .get("/api/foos")
                            .secure(Boolean.TRUE)
                        .with(SecurityMockMvcRequestPostProcessors.jwt().jwt(new Consumer<Jwt.Builder>() {
                            @Override
                            public void accept(Jwt.Builder builder) {
                                //builder.claim("preferred_username","mike@other.com");
                                //builder.audience()
                            }
                        }))
                )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(fooList.size())));

            //api.get("/greet").andExpect(content().string("Hello user! You are granted with [ROLE_AUTHORIZED_PERSONNEL]."));
        }*/

        @Test
        @DisplayName("FindAllWithDefaultJwtAuthentication")
        //@WithMockJwtAuth(authorities = {"ROLE_USER"}, claims = @OpenIdClaims(preferredUsername = "john@test.com"))
        void findAllDefaultJwtAuthentication() throws Exception {
            Foo abcFoo = new Foo("abc");
            abcFoo.setId(123L);
            List<Foo> fooList = new ArrayList<>();
            fooList.add(abcFoo);
            Mockito
                    .when(mockFooService.findAll())
                    .thenReturn(fooList);

            mockMvc
                .perform(MockMvcRequestBuilders
                            .get("/api/foos")
                        .secure(Boolean.TRUE)
                        .with(SecurityMockMvcRequestPostProcessors.jwt())
                )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(fooList.size())));
        }
    }



}
