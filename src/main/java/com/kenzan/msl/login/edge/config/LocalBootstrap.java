package com.kenzan.msl.login.edge.config;

import com.kenzan.msl.account.client.config.LocalAccountDataClientModule;
import com.netflix.governator.guice.BootstrapBinder;
import com.netflix.karyon.server.ServerBootstrap;

/**
 * @author Kenzan
 */
public class LocalBootstrap extends ServerBootstrap {

    @Override
    protected void configureBootstrapBinder(BootstrapBinder binder) {
        binder.install(new LocalAccountDataClientModule());
        binder.install(new LocalLoginEdgeModule());
        binder.install(new RestModule());
    }
}