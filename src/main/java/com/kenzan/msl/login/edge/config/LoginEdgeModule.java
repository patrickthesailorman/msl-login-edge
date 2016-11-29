package com.kenzan.msl.login.edge.config;

import com.google.inject.AbstractModule;
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

/**
 * Login Edge Module, a support class for Modules which reduces repetition and results in a more readable configuration
 * if no archaius.configurationSource.additionalUrls property is passed in, archaius uses default configuration. See readme to
 * understand how to pass in these variables
 *
 * @author Kenzan
 */
public class LoginEdgeModule extends AbstractModule {

    private String DEFAULT_CLIENT_PORT = "3000";

    private DynamicStringProperty CLIENT_PORT =
            DynamicPropertyFactory.getInstance().getStringProperty("clientPort", DEFAULT_CLIENT_PORT);

    @Override
    protected void configure() {
        bindConstant().annotatedWith(Names.named("clientPort")).to(CLIENT_PORT.get());

        requestStaticInjection(LoginEdgeApiServiceFactory.class);
        requestStaticInjection(LoginEdgeApiOriginFilter.class);

        bind(LoginEdgeSessionToken.class).to(LoginEdgeSessionTokenImpl.class).in(LazySingletonScope.get());

        bind(LoginEdgeService.class).to(LoginEdgeServiceImpl.class).in(LazySingletonScope.get());
        bind(LoginEdgeApiService.class).to(LoginEdgeApiServiceImpl.class).in(LazySingletonScope.get());
    }
}
