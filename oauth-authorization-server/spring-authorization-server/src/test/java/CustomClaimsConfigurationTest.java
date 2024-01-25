import com.baeldung.OAuth2AuthorizationServerApplication;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.text.ParseException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = OAuth2AuthorizationServerApplication.class)
@ActiveProfiles(value = "basic-claim")
public class CustomClaimsConfigurationTest {

    private static final String ISSUER_URL = "http://localhost:";
    private static final String USERNAME = "articles-client";
    private static final String PASSWORD = "secret";
    private static final String GRANT_TYPE = "client_credentials";

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int serverPort;

    @Test
    public void givenAccessToken_whenGetCustomClaim_thenSuccess() throws ParseException {
        String url = ISSUER_URL + serverPort + "/oauth2/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(USERNAME, PASSWORD);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", GRANT_TYPE);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<TokenDTO> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, TokenDTO.class);

        SignedJWT signedJWT = SignedJWT.parse(response.getBody().getAccessToken());
        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
        Map<String, Object> claims = claimsSet.getClaims();

        assertEquals("value-1", claims.get("claim-1"));
        assertEquals("value-2", claims.get("claim-2"));
    }

    static class TokenDTO {
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("token_type")
        private String tokenType;
        @JsonProperty("expires_in")
        private String expiresIn;
        @JsonProperty("scope")
        private String scope;

        public String getAccessToken() {
            return accessToken;
        }
    }

}