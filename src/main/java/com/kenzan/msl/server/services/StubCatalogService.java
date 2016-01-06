/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.services;

import com.google.common.base.Optional;
import com.kenzan.msl.server.mock.LogInMockData;
import rx.Observable;

import java.util.UUID;

/**
 * Implementation of the CatalogService interface that mocks the data it returns.
 */
public class StubCatalogService
    implements AuthenticationService {

    private LogInMockData logInMockData = new LogInMockData();

    /**
     * Gets UUID to use as sessionToken on valid credentials
     *
     * @param email user email
     * @param password user password
     * @return Observable<Optional<UUID>>
     */
    public Observable<Optional<UUID>> logIn(String email, String password) {
        return Observable.just(Optional.of(UUID.fromString(logInMockData.getAuthenticatedFlag(email, password)
            .getAuthenticated())));
    }

    /**
     * TODO
     *
     * @param email String
     * @return Observable<Void>
     */
    public Observable<Void> resetPassword(String email) {
        return Observable.empty();
    }

}
