package com.baeldung.oauth.client.web;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class ArticlesController {

    public ArticlesController(WebClient webClient) {
        this.webClient = webClient;
    }

    private WebClient webClient;

    @GetMapping(value = "/articles")
    public String[] getArticles(@RegisteredOAuth2AuthorizedClient("articles-client-authorization-code") OAuth2AuthorizedClient authorizedClient) {
        return this.webClient.get()
            .uri("http://127.0.0.1:8090/articles")
            .attributes(oauth2AuthorizedClient(authorizedClient))
            .retrieve()
            .bodyToMono(String[].class)
            .block();
    }
}