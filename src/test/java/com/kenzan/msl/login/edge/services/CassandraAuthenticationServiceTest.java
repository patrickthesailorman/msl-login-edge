package com.kenzan.msl.login.edge.services;

import com.google.common.base.Optional;
import com.kenzan.msl.account.client.TestConstants;
import com.kenzan.msl.account.client.services.CassandraAccountService;
import com.kenzan.msl.server.services.CassandraAuthenticationService;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import rx.Observable;

import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CassandraAccountService.class })
public class CassandraAuthenticationServiceTest {

    private TestConstants tc = TestConstants.getInstance();
    private CassandraAccountService cassandraAccountService;

    @Before
    public void init()
        throws Exception {

        PowerMock.mockStatic(CassandraAccountService.class);
        cassandraAccountService = EasyMock.createMock(CassandraAccountService.class);
        PowerMock.expectNew(CassandraAccountService.class).andReturn(cassandraAccountService);
        EasyMock.expect(CassandraAccountService.getInstance()).andReturn(cassandraAccountService).anyTimes();
    }

    @Test
    public void testSuccessfulLogIn() {
        mockAuthenticatedMethod();
        EasyMock.replay(cassandraAccountService);
        PowerMock.replayAll();

        CassandraAuthenticationService cassandraAuthenticationService = new CassandraAuthenticationService();
        Observable<Optional<UUID>> results = cassandraAuthenticationService.logIn(tc.USER_DAO.getUsername(),
                                                                                  tc.USER_DAO.getPassword());
        assertNotNull(results);
        assertEquals(tc.USER_DAO.getUserId(), results.toBlocking().first().get());
    }

    @Test
    public void testUnsuccessfulLogIn() {
        mockAuthenticatedMethod();
        EasyMock.replay(cassandraAccountService);
        PowerMock.replayAll();

        CassandraAuthenticationService cassandraAuthenticationService = new CassandraAuthenticationService();
        Observable<Optional<UUID>> results = cassandraAuthenticationService.logIn(tc.USER_DAO.getUsername(),
                                                                                  "INVALID_PASSOWRD");
        assertNotNull(results);
        assertFalse(results.toBlocking().first().isPresent());
    }

    @Test
    public void testResetPassword() {
        CassandraAuthenticationService cassandraAuthenticationService = new CassandraAuthenticationService();
        Observable<Void> results = cassandraAuthenticationService.resetPassword(tc.USER_DAO.getUsername());
        assertTrue(results.isEmpty().toBlocking().first());

    }

    private void mockAuthenticatedMethod() {
        EasyMock.expect(cassandraAccountService.getUser(EasyMock.anyString())).andReturn(Observable.just(tc.USER_DAO));

    }

}
