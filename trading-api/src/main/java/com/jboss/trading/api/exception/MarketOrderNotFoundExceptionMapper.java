package com.jboss.trading.api.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MarketOrderNotFoundExceptionMapper implements ExceptionMapper<MarketOrderNotFoundException> {

    @Override
    public Response toResponse(MarketOrderNotFoundException ex) {

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
