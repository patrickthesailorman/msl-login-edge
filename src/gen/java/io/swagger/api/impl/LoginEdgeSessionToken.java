package io.swagger.api.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.NewCookie;
import java.util.Date;
import java.util.UUID;

public class LoginEdgeSessionToken {

    private static String tokenValue;

    /**
     * Updates sessionToken on each coming request
     *
     * @param req HttpServletRequest
     */
    public static void updateSessionToken(HttpServletRequest req) {
        String sessionTokenValue = "";
        if (req.getCookies() != null) {
            Cookie[] cookies = req.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("sessionToken")) {
                    sessionTokenValue = cookies[i].getValue();
                }
            }
        }
        setTokenValue(sessionTokenValue);
    }

    /**
     * Checks whether or not the token is a valid UUID
     *
     * @return boolean
     */
    public static boolean isValidToken() {
        try {
            UUID.fromString(getTokenValue());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Returns the sessionToken in a cookie format, if a sessionToken is passed it updates the LoginEdgeSessionToken value with it
     *
     * @param sessionToken value to update LoginEdgeSessionToken.value with
     * @return NewCookie
     */
    public static NewCookie getSessionCookie(UUID sessionToken) {

        final String PATH = "/";
        final String DOMAIN = "msl.kenzanlabs.com";
        final int VERSION = 1;
        final String COMMENT = "";
        int MAX_AGE;

        if (null == sessionToken) {
            setTokenValue("");
            MAX_AGE = 0;
        } else {
            setTokenValue(sessionToken.toString());
            MAX_AGE = 24 * 60 * 60;
        }

        return new NewCookie(
                "sessionToken",
                getTokenValue(),
                PATH,
                DOMAIN,
                VERSION,
                COMMENT,
                MAX_AGE,
                getExpirationDate(),
                false,
                true
        );
    }

    /**
     * Returns an expiration date of a day
     *
     * @return Date
     */
    private static Date getExpirationDate () {
        return new Date(new Date().getTime() + (1000 * 60 * 60 * 24));
    }

    public static String getTokenValue() {
        return tokenValue;
    }

    public static void setTokenValue(String tokenValue) {
        LoginEdgeSessionToken.tokenValue = tokenValue;
    }
}
