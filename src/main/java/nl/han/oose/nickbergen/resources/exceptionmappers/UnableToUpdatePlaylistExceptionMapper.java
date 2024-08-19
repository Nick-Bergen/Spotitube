package nl.han.oose.nickbergen.resources.exceptionmappers;

import nl.han.oose.nickbergen.services.exceptions.UnableToUpdatePlaylistException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnableToUpdatePlaylistExceptionMapper implements ExceptionMapper<UnableToUpdatePlaylistException> {
    @Override
    public Response toResponse(UnableToUpdatePlaylistException e) {
        String returnMSG = "Unable to update playlist and fetch updated list.";
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(returnMSG)
                .build();
    }
}
