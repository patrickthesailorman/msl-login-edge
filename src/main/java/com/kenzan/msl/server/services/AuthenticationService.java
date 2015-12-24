/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.services;

import com.google.common.base.Optional;
import rx.Observable;

import java.util.UUID;

public interface AuthenticationService {

    /*
     * Gets the UUID for that user if password matches
     */
    Observable<Optional<UUID>> logIn(String email, String password);

}
