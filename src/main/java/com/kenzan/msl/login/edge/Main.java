package com.kenzan.msl.login.edge;

import com.google.common.base.Optional;
import com.google.inject.Injector;
import com.kenzan.msl.account.client.config.AccountDataClientModule;
import com.kenzan.msl.login.edge.config.LoginEdgeModule;
import com.kenzan.msl.login.edge.config.RestModule;
import com.netflix.governator.guice.LifecycleInjector;
import com.netflix.governator.lifecycle.LifecycleManager;
import io.swagger.api.LoginEdgeApi;
import io.swagger.api.impl.LoginEdgeApiOriginFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.HashMap;

public class Main {

  public static HashMap archaiusProperties = new HashMap<String, Optional<String>>();

  /**
   * Runs jetty server to expose jersey API
   * 
   * @param args String array
   * @throws Exception if server doesn't start
   */
  public static void main(String[] args) throws Exception {

    Injector injector =  LifecycleInjector.builder()
            .withModules(
                    new AccountDataClientModule(),
                    new LoginEdgeModule())
            .build()
            .createInjector();

    LifecycleManager manager = injector.getInstance(LifecycleManager.class);

    Server jettyServer = new Server(9001);
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    context.addFilter(LoginEdgeApiOriginFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
    jettyServer.setHandler(context);

    ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
    jerseyServlet.setInitOrder(0);

    jerseyServlet.setInitParameter("jersey.config.server.provider.classnames",
        LoginEdgeApi.class.getCanonicalName());

    try {
      manager.start();
      jettyServer.start();
      jettyServer.join();
    } finally {
      manager.close();
      jettyServer.destroy();
    }
  }
}
