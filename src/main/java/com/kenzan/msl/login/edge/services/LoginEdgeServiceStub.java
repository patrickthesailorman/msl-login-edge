/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.login.edge.services;

import com.google.common.base.Optional;
import com.kenzan.msl.login.edge.mock.LogInMockData;
import rx.Observable;

import java.util.UUID;

/**
 * Implementation of the CatalogService interface that mocks the data it returns.
 */
public class LoginEdgeServiceStub implements LoginEdgeService {

  private LogInMockData logInMockData = new LogInMockData();

  /**
   * Gets UUID to use as sessionToken on valid credentials
   *
   * @param email user email
   * @param password user password
   * @return Observable&lt;Optional&lt;UUID&gt;&gt;
   */
  public Observable<Optional<UUID>> logIn(String email, String password) {
    return Observable.just(Optional.of(UUID.fromString(logInMockData.getAuthenticatedFlag(email,
        password).getAuthenticated())));
  }

  /**
   * TODO
   * 
   * @param email String
   * @return Observable&lt;Void&gt;
   */
  public Observable<Void> resetPassword(String email) {
    return Observable.empty();
  }

  public boolean checkPassword(String password_plaintext, String stored_hash) {
    return true;
  }

}
