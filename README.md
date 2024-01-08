## Spring Security OAuth

I've just announced a new course, dedicated on exploring the new OAuth2 stack in Spring Security 5 - Learn Spring Security OAuth: 
http://bit.ly/github-lsso

</br></br></br>



## Build the Project
```
mvn clean install
```



## Projects/Modules
This project contains a number of modules, here is a quick description of what each module contains: 
- `oauth-rest` - Authorization Server (Keycloak), Resource Server and Angular App based on the new Spring Security 5 stack
- `oauth-jwt` - Authorization Server (Keycloak), Resource Server and Angular App based on the new Spring Security 5 stack, focused on JWT support
- `oauth-jws-jwk-legacy` - Authorization Server and Resource Server for JWS + JWK in a Spring Security OAuth2 Application
- `oauth-legacy` - Authorization Server, Resource Server, Angular and AngularJS Apps for legacy Spring Security OAuth2



## Run the Modules
You can run any sub-module using command line: 
```
mvn spring-boot:run
```

If you're using Spring STS, you can also import them and run them directly, via the Boot Dashboard 

You can then access the UI application - for example the module using the Password Grant - like this: 
`http://localhost:8084/`

You can login using these credentials, username:john and password:123 

## Run the Angular 7 Modules

- To run any of Angular7 front-end modules (_spring-security-oauth-ui-implicit-angular_ , _spring-security-oauth-ui-password-angular_ and _oauth-ui-authorization-code-angular_) , we need to build the app first:
```
mvn clean install
```

- Then we need to navigate to our Angular app directory:
```
cd src/main/resources
```

And run the command to download the dependencies:
```
npm install
```

- Finally, we will start our app:
```
npm start
```
- Note: Angular7 modules are commented out because these don't build on Jenkins as they need npm installed, but they build properly locally
- Note for Angular version < 4.3.0: You should comment out the HttpClient and HttpClientModule import in app.module and app.service.ts. These version rely on the HttpModule.

## Using the JS-only SPA OAuth Client
The main purpose of these projects are to analyze how OAuth should be carried out on Javascript-only Single-Page-Applications, using the authorization_code flow with PKCE.

The *clients-SPA-legacy/clients-js-only-react-legacy* project includes a very simple Spring Boot Application serving a couple of separate Single-Page-Applications developed in React.

It includes two pages:
  * a 'Step-By-Step' guide, where we analyze explicitly each step that we need to carry out to obtain an access token and request a secured resource
  * a 'Real Case' scenario, where we can log in, and obtain or use secured endpoints (provided by the Auth server and by a Custom server we set up)
  * the Article's Example Page, with the exact same code that is shown in the related article

The Step-By-Step guide supports using different providers (Authorization Servers) by just adding (or uncommenting) the corresponding entries in the static/*spa*/js/configs.js.

### The 'Step-by-Step' OAuth Client with PKCE page
After running the Spring Boot Application (a simple *mvn spring-boot:run* command will be enough), we can browse to *http://localhost:8080/pkce-stepbystep/index.html* and follow the steps to find out what it takes to obtain an access token using the Authorization Code with PKCE Flow.

When prompted the login form, we might need to create a user for our Application first.

### The 'Real-Case' OAuth Client with PKCE page
To use all the features contained in the *http://localhost:8080/pkce-realcase/index.html* page, we'll need to first start the resource server (clients-SPA-legacy/oauth-resource-server-auth0-legacy).

In this page, we can:
  * List the resources in our resource server (public, no permissions needed)
  * Add resources (we're requested the permissions to do that when logging in. For simplicity sake, we just request the existing 'profile' scope)
  * Remove resources (we actually can't accomplish this task, because the resource server requires the application to have permissions that were not included in the existing scopes)
  * 
  test build

