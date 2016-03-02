package com.kenzan.msl.server.mock;

import io.swagger.model.LoginSuccessResponse;
import io.swagger.model.StatusResponse;

import java.util.Date;

public class LogInMockData {

  LoginSuccessResponse loginMockData = new LoginSuccessResponse();

  /**
   * Mocks a success login
   *
   * @param email String
   * @param password String
   * @return LoginSuccessResponse
   */
  public LoginSuccessResponse getAuthenticatedFlag(String email, String password) {
    loginMockData.setAuthenticated(Long.toString(new Date().getTime()));
    return loginMockData;
  }

  /**
   * Mocks the logout action
   *
   * @return StatusResponse
   */
  public StatusResponse logOut() {
    StatusResponse response = new StatusResponse();
    response.setMessage("Successfully logged out");
    return response;
  }

}
