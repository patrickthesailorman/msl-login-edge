package com.kenzan.msl.login.edge.config;

import com.google.inject.AbstractModule;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.kenzan.msl.login.edge.services.LoginEdgeService;
import com.kenzan.msl.login.edge.services.LoginEdgeServiceImpl;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.governator.guice.lazy.LazySingletonScope;
import io.swagger.api.LoginEdgeApiService;
import io.swagger.api.factories.LoginEdgeApiServiceFactory;
import io.swagger.api.impl.LoginEdgeApiOriginFilter;
import io.swagger.api.impl.LoginEdgeApiServiceImpl;
import io.swagger.api.impl.LoginEdgeSessionToken;
import io.swagger.api.impl.LoginEdgeSessionTokenImpl;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Properties;

/**
 * @author Kenzan
 */
public class LoginEdgeModule  extends AbstractModule {

    private String DEFAULT_CLIENT_PORT = "3000";

    private DynamicStringProperty CLIENT_PORT =
            DynamicPropertyFactory.getInstance().getStringProperty("clientPort", DEFAULT_CLIENT_PORT);

    @Override
    protected void configure() {
        configureArchaius();
        bindConstant().annotatedWith(Names.named("clientPort")).to(CLIENT_PORT.get());

        requestStaticInjection(LoginEdgeApiServiceFactory.class);
        requestStaticInjection(LoginEdgeApiOriginFilter.class);

        bind(LoginEdgeSessionToken.class).to(LoginEdgeSessionTokenImpl.class).in(
                LazySingletonScope.get());
        bind(LoginEdgeService.class).to(LoginEdgeServiceImpl.class).in(LazySingletonScope.get());
        bind(LoginEdgeApiService.class).to(LoginEdgeApiServiceImpl.class).in(LazySingletonScope.get());
    }

    private void configureArchaius() {
        Properties props = System.getProperties();
        String ENV = props.getProperty("env");
        if (StringUtils.isEmpty(ENV) || ENV.toLowerCase().contains("local")) {
            String configUrl = "file://" + System.getProperty("user.dir") + "/../msl-login-edge-config/edge-config.properties";
            File f = new File(configUrl);
            if(f.exists() && !f.isDirectory()) {
                System.setProperty("archaius.configurationSource.additionalUrls", configUrl);
            }
        }
    }
}
