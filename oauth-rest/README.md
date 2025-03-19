## Spring Security OAuth - New Stack

### Relevant information:

1. `oauth-authorization-server` is a Keycloak Authorization Server wrapped as a Spring Boot application
2. There is one OAuth Client registered in the Authorization Server:
   1. Client Id: newClient
   2. Client secret: newClientSecret
   3. Redirect Uri: http://localhost:8089/
2. There are two OAuth Angular Front-end Apps:
   1. `oauth-ui-authorization-code-angular` - A simple Angular App
   2. `oauth-ui-authorization-code-angular-zuul` - An Angular App hosted as a Boot Application with embedded Zuul proxy
3. `oauth-resource-server` is a Spring Boot based RESTFul API, acting as a backend Application
4. There is a Spring-based OAuth 2 Client, `oauth-client-spring`
5. There are two users registered in the Authorization Server:
   1. john@test.com / 123
   2. mike@other.com / pass
6. The module uses the new OAuth stack with Java 13.

## Relevant Articles: 

- [Spring REST API + OAuth2 + Angular](https://www.baeldung.com/rest-api-spring-oauth2-angular)
- [OAuth2 for a Spring REST API – Handle the Refresh Token in Angular](https://www.baeldung.com/spring-security-oauth2-refresh-token-angular)
- [Logout in an OAuth Secured Application](https://www.baeldung.com/logout-spring-security-oauth)
