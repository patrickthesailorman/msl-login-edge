package com.kenzan.msl.login.edge.config;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.google.inject.Scopes;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import io.swagger.api.LoginEdgeApi;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kenzan
 */
public class RestModule extends JerseyServletModule {

    @Override
    protected void configureServlets() {
        bind(LoginEdgeApi.class);

        Map<String, String> initParams = new HashMap<String, String>();
        initParams.put(PackagesResourceConfig.PROPERTY_PACKAGES, "io.swagger.api");
        bind(JacksonJaxbJsonProvider.class).in(Scopes.SINGLETON);

        serve("/*").with(GuiceContainer.class, initParams);
    }

}