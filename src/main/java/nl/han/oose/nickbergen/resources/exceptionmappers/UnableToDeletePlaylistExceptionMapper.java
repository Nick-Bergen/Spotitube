package nl.han.oose.nickbergen.resources.exceptionmappers;

import nl.han.oose.nickbergen.services.exceptions.UnableToDeletePlaylistException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnableToDeletePlaylistExceptionMapper implements ExceptionMapper<UnableToDeletePlaylistException> {
    @Override
    public Response toResponse(UnableToDeletePlaylistException e) {
        String returnMSG = "Unable to delete playlist and fetch updated list.";
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(returnMSG)
                .build();
    }
}
