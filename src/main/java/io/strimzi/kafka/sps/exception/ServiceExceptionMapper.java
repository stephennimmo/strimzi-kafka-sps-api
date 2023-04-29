package io.strimzi.kafka.sps.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

@Provider
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {

    @Override
    public Response toResponse(ServiceException e) {
        ErrorResponse.ErrorMessage errorMessage = new ErrorResponse.ErrorMessage(null, e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(null, List.of(errorMessage))).build();
    }

}