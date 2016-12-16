package com.kenzan.msl.login.edge.services;

import com.google.common.base.Optional;
import com.kenzan.msl.account.client.TestConstants;
import com.kenzan.msl.account.client.dto.UserDto;
import com.kenzan.msl.account.client.services.AccountDataClientService;
import com.kenzan.msl.account.client.services.AccountDataClientServiceImpl;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import rx.Observable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ AccountDataClientService.class })
public class LoginEdgeServiceImplTest {

    private TestConstants tc = TestConstants.getInstance();

    @Mock
    private AccountDataClientServiceImpl cassandraAccountService;

    @Before
    public void init() throws Exception {

        cassandraAccountService = PowerMockito.mock(AccountDataClientServiceImpl.class);
    }

    @Test
    public void testSuccessfulLogIn() {
        // hashed value of "password"
        String hashPassword = "$2a$12$QTp1ZMDtJEWWzEmNrrlZFeoy889aWXfq/vduggMb7xq6oMOhEKUyG";
        UserDto user = tc.USER_DTO;
        user.setPassword(hashPassword);

        Mockito.when(cassandraAccountService.getUserByUsername("username")).thenReturn(Observable.just(user));

        LoginEdgeServiceImpl cassandraAuthenticationService = new LoginEdgeServiceImpl(cassandraAccountService);
        Observable<Optional<UUID>> results =
                        cassandraAuthenticationService.logIn(tc.USER_DTO.getUsername(), "password");
        assertNotNull(results);
        assertEquals(tc.USER_DTO.getUserId(), results.toBlocking().first().get());
    }

    @Test
    public void testUnsuccessfulLogIn() {

        PowerMock.replayAll();
        Mockito.when(cassandraAccountService.getUserByUsername("username")).thenReturn(Observable.just(tc.USER_DTO));

        LoginEdgeServiceImpl cassandraAuthenticationService = new LoginEdgeServiceImpl(cassandraAccountService);
        Observable<Optional<UUID>> results =
                        cassandraAuthenticationService.logIn(tc.USER_DTO.getUsername(), "INVALID_PASSWORD");
        assertNotNull(results);
        assertFalse(results.toBlocking().first().isPresent());
    }

    @Test
    public void testResetPassword() {

        LoginEdgeServiceImpl cassandraAuthenticationService = new LoginEdgeServiceImpl(cassandraAccountService);
        Observable<Void> results = cassandraAuthenticationService.resetPassword(tc.USER_DTO.getUsername());
        assertTrue(results.isEmpty().toBlocking().first());

    }
}
