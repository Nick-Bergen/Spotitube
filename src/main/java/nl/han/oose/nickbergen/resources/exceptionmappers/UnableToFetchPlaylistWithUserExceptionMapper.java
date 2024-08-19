package nl.han.oose.nickbergen.resources.exceptionmappers;

import nl.han.oose.nickbergen.services.exceptions.UnableToFetchPlaylistsWithUserException;
import nl.han.oose.nickbergen.services.exceptions.UnableToFetchUsernameWithTokenException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnableToFetchPlaylistWithUserExceptionMapper implements ExceptionMapper<UnableToFetchPlaylistsWithUserException>{
    @Override
    public Response toResponse(UnableToFetchPlaylistsWithUserException e) {
        String returnMSG = "Unable to fetch playlists from user.";
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(returnMSG)
                .build();
    }
}
