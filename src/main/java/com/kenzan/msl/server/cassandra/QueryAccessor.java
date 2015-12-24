package com.kenzan.msl.server.cassandra;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

import java.util.Date;
import java.util.UUID;

@Accessor
public interface QueryAccessor {
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    public ResultSet logIn(@Param("username") String username);
}