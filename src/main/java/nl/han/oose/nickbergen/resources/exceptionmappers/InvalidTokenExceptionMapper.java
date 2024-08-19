package nl.han.oose.nickbergen.resources.exceptionmappers;

import nl.han.oose.nickbergen.services.exceptions.InvalidTokenException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidTokenExceptionMapper implements ExceptionMapper<InvalidTokenException> {
    @Override
    public Response toResponse(InvalidTokenException e) {
        String responseMSG = "Invalid token, unable to fetch username";
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .entity(responseMSG)
                .build();
    }
}
