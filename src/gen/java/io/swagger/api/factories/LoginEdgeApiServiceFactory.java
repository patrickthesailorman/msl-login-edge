package io.swagger.api.factories;

import io.swagger.api.LoginEdgeApiService;
import io.swagger.api.impl.LoginEdgeApiServiceImpl;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2016-01-25T12:48:15.318-06:00")
public class LoginEdgeApiServiceFactory {

   private final static LoginEdgeApiService service = new LoginEdgeApiServiceImpl();

   public static LoginEdgeApiService getLoginEdgeApi()
   {
      return service;
   }
}
