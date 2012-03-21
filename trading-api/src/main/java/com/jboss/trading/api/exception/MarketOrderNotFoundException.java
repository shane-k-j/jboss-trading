package com.jboss.trading.api.exception;

public class MarketOrderNotFoundException extends Exception {

    private static final String MSG_PREFIX_STR = "Market Order Not Found, Id: ";

    private MarketOrderNotFoundException(String message) {

        super(message);
    }

    public static MarketOrderNotFoundException getInstance(Integer marketOrderId) {

        String message = MSG_PREFIX_STR + marketOrderId;

        MarketOrderNotFoundException ex = new MarketOrderNotFoundException(message);

        return ex;
    }
}
