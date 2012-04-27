package com.jboss.trading.rest.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.jboss.trading.api.exception.LimitOrderNotFoundException;

@Provider
public class LimitOrderNotFoundExceptionMapper implements ExceptionMapper<LimitOrderNotFoundException> {

    @Override
    public Response toResponse(LimitOrderNotFoundException ex) {

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
