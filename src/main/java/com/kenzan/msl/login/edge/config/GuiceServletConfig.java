package com.kenzan.msl.login.edge.config;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.kenzan.msl.account.client.config.AccountDataClientModule;
import com.netflix.governator.guice.LifecycleInjector;

/**
 * @author Kenzan
 */
public class GuiceServletConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return LifecycleInjector.builder()
                .withModules(
                        new RestModule(),
                        new AccountDataClientModule(),
                        new LoginEdgeModule())
                .build()
                .createInjector();
    }
}

