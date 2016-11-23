package com.kenzan.msl.login.edge.config;

import com.kenzan.msl.account.client.config.AccountDataClientModule;
import com.netflix.governator.guice.BootstrapBinder;
import com.netflix.karyon.server.ServerBootstrap;

/**
 * This class is the point where the karyon environment in bootstrapped which more or less is the bootstrapping of Governator
 *
 * @author Kenzan
 */
public class Bootstrap extends ServerBootstrap {

    @Override
    protected void configureBootstrapBinder(BootstrapBinder binder) {
        binder.install(new AccountDataClientModule());
        binder.install(new LoginEdgeModule());
        binder.install(new RestModule());
    }
}
