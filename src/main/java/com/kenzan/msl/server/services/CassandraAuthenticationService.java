/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.services;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import com.google.common.base.Optional;
import com.kenzan.msl.server.cassandra.CassandraConstants;
import com.kenzan.msl.server.cassandra.QueryAccessor;
import com.kenzan.msl.server.cassandra.query.AuthenticationQuery;

import java.util.UUID;
import rx.Observable;

/**
 * Implementation of the CassandraAuthenticationService interface that retrieves its data from a Cassandra cluster.
 */
public class CassandraAuthenticationService
    implements AuthenticationService {

    private QueryAccessor queryAccessor;
    private MappingManager mappingManager;

    public CassandraAuthenticationService() {
        // TODO: Get the contact point from config param
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        // TODO: Get the keyspace from config param
        Session session = cluster.connect(CassandraConstants.MSL_KEYSPACE);

        mappingManager = new MappingManager(session);
        queryAccessor = mappingManager.createAccessor(QueryAccessor.class);
    }

    /**
     * Gets UUID to use as sessionToken on valid credentials
     *
     * @param email user email
     * @param password user password
     * @return Observable<UUID>
     */
    // TODO move this to a dedicated CassandraAuthenticationService
    public Observable<Optional<UUID>> logIn(String email, String password) {
        return Observable.just(AuthenticationQuery.authenticate(queryAccessor, email, password));
    }
}
