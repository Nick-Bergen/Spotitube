package nl.han.oose.nickbergen.resources.exceptionmappers;

import nl.han.oose.nickbergen.services.exceptions.UnableToFetchUsernameWithTokenException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnableToFetchUsernameWithTokenExceptionMapper implements ExceptionMapper<UnableToFetchUsernameWithTokenException> {
    @Override
    public Response toResponse(UnableToFetchUsernameWithTokenException e) {
        String returnMSG = "Unable to fetch username with provided usertoken.";
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(returnMSG)
                .build();
    }
}
