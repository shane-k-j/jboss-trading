package com.jboss.trading.api.exception;

public class LimitOrderNotFoundException extends Exception {

    private static final String MSG_PREFIX_STR = "Limit Order Not Found, Id: ";

    private LimitOrderNotFoundException(String message) {

        super(message);
    }

    public static LimitOrderNotFoundException getInstance(Integer limitOrderId) {

        String message = MSG_PREFIX_STR + limitOrderId;

        LimitOrderNotFoundException ex = new LimitOrderNotFoundException(message);

        return ex;
    }
}
