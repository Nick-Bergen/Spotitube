package nl.han.oose.nickbergen.resources;

import nl.han.oose.nickbergen.datasource.PlaylistDAO;
import nl.han.oose.nickbergen.datasource.TokenDAO;
import nl.han.oose.nickbergen.services.dto.PlaylistDTO;
import nl.han.oose.nickbergen.services.exceptions.InvalidTokenException;
import nl.han.oose.nickbergen.services.exceptions.UnableToFetchUsernameWithTokenException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistResource {
    private TokenDAO tokenDao;
    private PlaylistDAO playListDao;

    @Inject
    public void setTokenDAO(TokenDAO tokenDAO) {this.tokenDao = tokenDAO;}

    @Inject
    public void setplayListDao(PlaylistDAO playListDao) {this.playListDao = playListDao;}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response Playlists(@QueryParam("token") String userToken){
        //get username
        String username = tokenDao.getUserFromToken(userToken);

        //get all playlists with username as the owner and return the DTO
        return Response
                .status(Response.Status.ACCEPTED)
                .entity(playListDao.getPlaylistsDTOFromUser(username))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response AddPlaylist(@QueryParam("token") String userToken, PlaylistDTO playlistDTO){
        String username = tokenDao.getUserFromToken(userToken);
        //Attempt to create new playlist
        return  Response
                .status(Response.Status.ACCEPTED)
                .entity(playListDao.CreatePlaylist(username, playlistDTO))
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response DeletePlaylist(@QueryParam("token") String userToken, @PathParam("id") int id){
        String username = tokenDao.getUserFromToken(userToken);
        //Attempt to delete the playlist
        return  Response
                .status(Response.Status.ACCEPTED)
                .entity(playListDao.DeletePlaylist(username, id))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response EditPlaylist(@QueryParam("token") String userToken, @PathParam("id") int id, PlaylistDTO playlistDTO){
        String username = tokenDao.getUserFromToken(userToken);
        //Attempt to update the playlist
        return  Response
                .status(Response.Status.ACCEPTED)
                .entity(playListDao.UpdatePlaylist(username, playlistDTO))
                .build();
    }
}
