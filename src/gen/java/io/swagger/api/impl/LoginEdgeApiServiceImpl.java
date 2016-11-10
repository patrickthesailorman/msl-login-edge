package io.swagger.api.impl;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.kenzan.msl.login.edge.services.LoginEdgeService;
import io.swagger.api.*;

import io.swagger.model.LoginSuccessResponse;
import io.swagger.model.ErrorResponse;
import io.swagger.model.StatusResponse;

import java.util.Date;

import io.swagger.api.NotFoundException;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2016-01-25T12:48:15.318-06:00")
public class LoginEdgeApiServiceImpl extends LoginEdgeApiService {

    private LoginEdgeService loginEdgeService;
    private LoginEdgeSessionToken loginEdgeSessionToken;

    @Inject
    public LoginEdgeApiServiceImpl (final LoginEdgeService loginEdgeService, final LoginEdgeSessionToken loginEdgeSessionToken) {
        this.loginEdgeService = loginEdgeService;
        this.loginEdgeSessionToken = loginEdgeSessionToken;
    }

    @Override
    public Response login(String email, String password) throws NotFoundException {
        // Validate required parameters
        if ( StringUtils.isEmpty(email) ) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new LoginEdgeApiResponseMessage(LoginEdgeApiResponseMessage.ERROR,
                            "Required parameter 'email' is null or empty.")).build();
        }
        if ( StringUtils.isEmpty(password) ) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new LoginEdgeApiResponseMessage(LoginEdgeApiResponseMessage.ERROR,
                            "Required parameter 'password' is null or empty.")).build();
        }

        Optional<UUID> optSessionToken;
        try {
            optSessionToken = loginEdgeService.logIn(email, password).toBlocking().first();
        }
        catch ( Exception e ) {
            e.printStackTrace();

            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Server error: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }

        if ( !optSessionToken.isPresent() ) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Invalid credentials");
            return Response.status(Response.Status.UNAUTHORIZED).entity(errorResponse).build();
        }

        LoginSuccessResponse loginSuccessResponse = new LoginSuccessResponse();
        loginSuccessResponse.setAuthenticated(new Date().toString());
        NewCookie cookie = loginEdgeSessionToken.getSessionCookie(optSessionToken.get());
        LoginEdgeApiResponseMessage response = new LoginEdgeApiResponseMessage(LoginEdgeApiResponseMessage.OK, "success", loginSuccessResponse);

        return Response.ok()
                .cookie(cookie)
                .entity(response)
                .build();
    }

    @Override
    public Response logout() throws NotFoundException {
        StatusResponse response = new StatusResponse();
        response.setMessage("Successfully logged out");

        return Response.ok().cookie(loginEdgeSessionToken.getSessionCookie(null))
                .entity(new LoginEdgeApiResponseMessage(LoginEdgeApiResponseMessage.OK, "success", response)).build();
    }

    @Override
    public Response resetPassword(String email)
            throws NotFoundException {
        // Validate required parameters
        if (StringUtils.isEmpty(email)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new LoginEdgeApiResponseMessage(LoginEdgeApiResponseMessage.ERROR, "Required parameter 'email' is null or empty.")).build();
        }

        // do some magic!
        return Response.ok()
                .entity(new LoginEdgeApiResponseMessage(LoginEdgeApiResponseMessage.OK, "magic!")).build();
    }
  
}
