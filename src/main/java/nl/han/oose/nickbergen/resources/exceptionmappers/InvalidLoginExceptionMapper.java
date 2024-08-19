package nl.han.oose.nickbergen.resources.exceptionmappers;

import nl.han.oose.nickbergen.services.exceptions.InvalidLoginException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidLoginExceptionMapper implements ExceptionMapper<InvalidLoginException> {
    @Override
    public Response toResponse(InvalidLoginException e) {
        String responseMSG = "Invalid Login attempt.";
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .entity(responseMSG)
                .build();
    }
}
