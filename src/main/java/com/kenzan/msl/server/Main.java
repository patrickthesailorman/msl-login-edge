package com.kenzan.msl.server;

import com.google.common.base.Optional;
import com.netflix.governator.annotations.Modules;
import io.swagger.api.LoginEdgeApi;
import io.swagger.api.impl.LoginEdgeApiOriginFilter;
import netflix.adminresources.resources.KaryonWebAdminModule;
import netflix.karyon.KaryonBootstrap;
import netflix.karyon.ShutdownModule;
import netflix.karyon.archaius.ArchaiusBootstrap;
import netflix.karyon.servo.KaryonServoModule;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.HashMap;

@ArchaiusBootstrap
@KaryonBootstrap(name = "msl-login-edge")
@Modules(include = {ShutdownModule.class, KaryonWebAdminModule.class, // Uncomment this to enable
                                                                      // WebAdmin
    // KaryonEurekaModule.class, // Uncomment this to enable Eureka client.
    KaryonServoModule.class})
public class Main {

  public static HashMap archaiusProperties = new HashMap<String, Optional<String>>();

  /**
   * Runs jetty server to expose jersey API
   * 
   * @param args String array
   * @throws Exception if server doesn't start
   */
  public static void main(String[] args) throws Exception {

    archaiusProperties.put("region",
        Optional.fromNullable(System.getProperty("archaius.deployment.region")));
    archaiusProperties.put("domainName",
        Optional.fromNullable(System.getProperty("archaius.deployment.domainName")));

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

      jettyServer.start();
      jettyServer.join();

    } finally {
      jettyServer.destroy();
    }
  }
}
