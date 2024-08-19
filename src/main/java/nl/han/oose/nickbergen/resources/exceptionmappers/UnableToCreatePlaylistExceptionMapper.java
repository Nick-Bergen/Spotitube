package nl.han.oose.nickbergen.resources.exceptionmappers;

import nl.han.oose.nickbergen.services.exceptions.UnableToCreatePlaylistException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnableToCreatePlaylistExceptionMapper implements ExceptionMapper<UnableToCreatePlaylistException> {
    @Override
    public Response toResponse(UnableToCreatePlaylistException e) {
        String returnMSG = "Unable to add playlist and fetch updated list.";
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(returnMSG)
                .build();
    }
}
