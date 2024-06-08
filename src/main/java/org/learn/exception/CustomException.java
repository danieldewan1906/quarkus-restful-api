package org.learn.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomException implements ExceptionMapper<Exception> {

    @Inject
    ObjectMapper mapper;

    @Override
    public Response toResponse(Exception e) {

        int code = 500;
        if (e instanceof WebApplicationException) {
            code = ((WebApplicationException) e).getResponse().getStatus();
        }

        ObjectNode exceptionJson = mapper.createObjectNode();
        exceptionJson.put("code", code);

        if (e.getMessage() != null) {
            exceptionJson.put("error", e.getMessage());
        }

        return Response.status(code).entity(exceptionJson).build();
    }
}
