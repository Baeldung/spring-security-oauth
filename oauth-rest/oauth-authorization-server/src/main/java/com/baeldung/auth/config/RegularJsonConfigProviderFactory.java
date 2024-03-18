package com.baeldung.auth.config;

import org.keycloak.services.util.JsonConfigProviderFactory;

public class RegularJsonConfigProviderFactory extends JsonConfigProviderFactory {
    @Override
    protected Properties getProperties() {
        return new SystemEnvProperties(System.getenv());
    }
}
