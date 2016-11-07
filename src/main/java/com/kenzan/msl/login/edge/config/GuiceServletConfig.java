package com.kenzan.msl.login.edge.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.kenzan.msl.account.client.config.AccountDataClientModule;

/**
 * @author Kenzan
 */
public class GuiceServletConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new RestModule(),
                new AccountDataClientModule(),
                new LoginEdgeModule());
    }
}

