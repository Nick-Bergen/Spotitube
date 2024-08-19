package nl.han.oose.nickbergen.resources;

import nl.han.oose.nickbergen.datasource.LoginDAO;
import nl.han.oose.nickbergen.datasource.PlaylistDAO;
import nl.han.oose.nickbergen.datasource.TokenDAO;
import nl.han.oose.nickbergen.services.dto.LoginReceivedDTO;
import nl.han.oose.nickbergen.services.dto.PlaylistDTO;
import nl.han.oose.nickbergen.services.dto.TrackDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PlaylistResourceTest {
    private PlaylistResource sut;
    private TokenDAO tokenDao;
    private PlaylistDAO playListDao;

    @BeforeEach
    void instantiate(){
        sut = new PlaylistResource();

        tokenDao = mock(TokenDAO.class);
        playListDao = mock(PlaylistDAO.class);


        sut.setTokenDAO(tokenDao);
        sut.setplayListDao(playListDao);
    }

    @Test
    public void GetAllPlaylistsTest(){
        //Arrange
        String token = "0005406a-ea59-4cca-b54d-dd5002457ce2";

        //Act
        sut.Playlists(token);

        //Assert
        verify(playListDao).getPlaylistsDTOFromUser(tokenDao.getUserFromToken(token));
    }

    @Test
    public void AddPlaylistTest(){
        //Arrange
        String token = "0005406a-ea59-4cca-b54d-dd5002457ce2";
        List<TrackDTO> trackList = new ArrayList<>();
        PlaylistDTO playlistDTO = new PlaylistDTO(0, "playlistName", true, trackList);

        //Act
        sut.AddPlaylist(token, playlistDTO);

        //Assert
        verify(playListDao).CreatePlaylist(tokenDao.getUserFromToken(token),playlistDTO);
    }

    @Test
    public void DeletePlaylistTest(){
        //Arrange
        String token = "0005406a-ea59-4cca-b54d-dd5002457ce2";
        int id = 1;

        //Act
        sut.DeletePlaylist(token, id);

        //Assert
        verify(playListDao).DeletePlaylist(tokenDao.getUserFromToken(token),id);
    }

    @Test
    public void EditPlaylistTest(){
        //Arrange
        String token = "0005406a-ea59-4cca-b54d-dd5002457ce2";
        int id = 1;
        List<TrackDTO> trackList = new ArrayList<>();
        PlaylistDTO playlistDTO = new PlaylistDTO(0, "playlistName", true, trackList);

        //Act
        sut.EditPlaylist(token, id, playlistDTO);

        //Assert
        verify(playListDao).UpdatePlaylist(tokenDao.getUserFromToken(token),playlistDTO);
    }
}
