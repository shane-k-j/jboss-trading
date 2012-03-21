package com.jboss.trading.api.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PlaceOrderExceptionMapper implements ExceptionMapper<PlaceOrderException> {

    @Override
    public Response toResponse(PlaceOrderException ex) {

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
