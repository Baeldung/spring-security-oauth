## Spring Security JWT with OAuth

### Relevant information:

1. `jwt-auth-server` is a Keycloak Authorization Server wrapped as a Spring Boot application
2. There is one OAuth Client registered in the Authorization Server:
   1. Client Id: jwtClient
   2. Client secret: jwtClientSecret
   3. Redirect Uri: http://localhost:8084/
3. There are two users registered in the Authorization Server:
   1. john@test.com / 123
   2. mike@other.com / pass
4. `jwt-resource-server` is a Spring Boot Resource Server which exchanges OAuth Tokens with the above auth-server and dishes out a couple of APIs - /user/info and /api/foos/**
5. `oauth-ui-authorization-code-angular-jwt` is a Angular Client
6. The module uses the new OAuth stack with Java 13.
    
### Relevant Articles:

- [Using JWT with Spring Security OAuth](https://www.baeldung.com/spring-security-oauth-jwt)
- [Customizing Themes for Keycloak](https://www.baeldung.com/spring-keycloak-custom-themes)
- [Customizing the Login Page for Keycloak](https://www.baeldung.com/keycloak-custom-login-page)
- [Custom User Attributes with Keycloak](https://www.baeldung.com/keycloak-custom-user-attributes)
- [Keycloak User Self-Registration](https://www.baeldung.com/keycloak-user-registration) 
