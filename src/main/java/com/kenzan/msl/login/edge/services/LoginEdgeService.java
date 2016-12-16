/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.login.edge.services;

import com.google.common.base.Optional;
import rx.Observable;

import java.util.UUID;

public interface LoginEdgeService {

  /*
   * Gets the UUID for that user if password matches
   */
  Observable<Optional<UUID>> logIn(String email, String password);

  Observable<Void> resetPassword(String email);

  boolean checkPassword(String password_plaintext, String stored_hash);

}
