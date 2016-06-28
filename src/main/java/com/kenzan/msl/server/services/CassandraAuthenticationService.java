/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.services;

import com.google.common.base.Optional;
import com.kenzan.msl.account.client.dto.UserDto;
import com.kenzan.msl.account.client.services.CassandraAccountService;

import java.util.UUID;
import rx.Observable;

/**
 * Implementation of the CassandraAuthenticationService interface that retrieves its data from a
 * Cassandra cluster.
 */
public class CassandraAuthenticationService implements AuthenticationService {

  private final CassandraAccountService cassandraAccountService;

  public CassandraAuthenticationService(final CassandraAccountService cassandraAccountService) {
    this.cassandraAccountService = cassandraAccountService;
  }

  /**
   * Gets UUID to use as sessionToken on valid credentials
   *
   * @param email user email
   * @param password user password
   * @return Observable&lt;UUID&gt;
   */
  public Observable<Optional<UUID>> logIn(String email, String password) {
    return Observable.just(authenticate(email, password));
  }

  /**
   * Returns UUID of user if email and password are valid
   *
   * @param username user email
   * @param password user password
   * @return Optional&lt;UUID&gt;
   */
  private Optional<UUID> authenticate(String username, String password) {
    Observable<UserDto> observableUser = cassandraAccountService.getUserByUsername(username);
    if (observableUser.isEmpty().toBlocking().first()) {
      return Optional.absent();
    } else {
      UserDto user = observableUser.toBlocking().first();
      if (user.getPassword().equals(password)) {
        return Optional.of(user.getUserId());
      }
    }
    return Optional.absent();
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
}
