package com.baeldung.jwt.config;

import java.util.HashMap;
import java.util.Map;

import org.jboss.resteasy.core.ResteasyContext;
import org.keycloak.common.util.ResteasyProvider;

public class Resteasy3Provider implements ResteasyProvider {

    @Override
    public <R> R getContextData(Class<R> type) {
        return ResteasyContext.getContextData(type);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void pushDefaultContextObject(Class type, Object instance) {
        Map<Class<?>, Object> map = new HashMap<>();
        map.put(type, instance);
        ResteasyContext.pushContextDataMap(map);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void pushContext(Class type, Object instance) {
        ResteasyContext.pushContext(type, instance);
    }

    @Override
    public void clearContextData() {
        ResteasyContext.clearContextData();
    }

}