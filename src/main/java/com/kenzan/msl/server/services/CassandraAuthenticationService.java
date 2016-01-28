/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.services;

import com.google.common.base.Optional;
import com.kenzan.msl.account.client.dao.UserDao;
import com.kenzan.msl.account.client.services.CassandraAccountService;

import java.util.UUID;
import rx.Observable;

/**
 * Implementation of the CassandraAuthenticationService interface that retrieves its data from a
 * Cassandra cluster.
 */
public class CassandraAuthenticationService
    implements AuthenticationService {

    private CassandraAccountService cassandraAccountService = CassandraAccountService.getInstance();

    /**
     * Gets UUID to use as sessionToken on valid credentials
     *
     * @param email user email
     * @param password user password
     * @return Observable<UUID>
     */
    public Observable<Optional<UUID>> logIn(String email, String password) {
        return Observable.just(authenticate(email, password));
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

    /**
     * Returns UUID of user if email and password are valid
     *
     * @param username user email
     * @param password user password
     * @return Optional UUID
     */
    private Optional<UUID> authenticate(String username, String password) {
        UserDao user = cassandraAccountService.getUser(username).toBlocking().first();

        if ( user.getPassword().equals(password) ) {
            return Optional.of(user.getUserId());
        }

        return Optional.absent();
    }
}
