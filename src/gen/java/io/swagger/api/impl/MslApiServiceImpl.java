package io.swagger.api.impl;

import com.google.common.base.Optional;
import com.kenzan.msl.server.services.AuthenticationService;
import com.kenzan.msl.server.services.CassandraAuthenticationService;
import io.swagger.api.*;

import io.swagger.model.LoginSuccessResponse;
import io.swagger.model.ErrorResponse;
import io.swagger.model.StatusResponse;
import java.util.Date;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import javax.ws.rs.core.Response;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-12-28T15:59:58.895-06:00")
public class MslApiServiceImpl extends MslApiService {

    private AuthenticationService authenticationServiceService = new CassandraAuthenticationService();
    private MslSessionToken mslSessionToken = MslSessionToken.getInstance();

    @Override
    public Response login(String email, String password) throws NotFoundException {
        // Validate required parameters
        if ( StringUtils.isEmpty(email) ) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR,
                            "Required parameter 'email' is null or empty.")).build();
        }
        if ( StringUtils.isEmpty(password) ) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR,
                            "Required parameter 'password' is null or empty.")).build();
        }

        Optional<UUID> optSessionToken;
        try {
            optSessionToken = authenticationServiceService.logIn(email, password).toBlocking().first();
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

        return Response.ok().cookie(mslSessionToken.getSessionCookie(optSessionToken.get()))
                .entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success", loginSuccessResponse)).build();
    }

    @Override
    public Response logout() throws NotFoundException {
        StatusResponse response = new StatusResponse();
        response.setMessage("Successfully logged out");

        return Response.ok().cookie(mslSessionToken.getSessionCookie(null))
                .entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success", response)).build();
    }

    @Override
    public Response resetPassword(String email)
            throws NotFoundException {
        // Validate required parameters
        if (StringUtils.isEmpty(email)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'email' is null or empty.")).build();
        }

        // do some magic!
        return Response.ok()
                .entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
    }
  
}
