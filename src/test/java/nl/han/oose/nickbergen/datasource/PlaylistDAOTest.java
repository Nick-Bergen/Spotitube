package nl.han.oose.nickbergen.datasource;

import nl.han.oose.nickbergen.datasource.util.DatabaseProperties;

import nl.han.oose.nickbergen.services.dto.PlaylistDTO;
import nl.han.oose.nickbergen.services.dto.TrackDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class PlaylistDAOTest {
    private PlaylistDAO sut;
    private DatabaseProperties databaseProperties;

    @BeforeEach
    void instantiate(){
        sut = new PlaylistDAO();

        databaseProperties = new DatabaseProperties();

        sut.setDatabaseProperties(databaseProperties);
    }

    @Test
    public void getValidPlaylistsFromUserTest(){
        //Arrange
        String username = "testy";
        //Act

        //Assert
        assertDoesNotThrow(() -> sut.getPlaylistsDTOFromUser(username));
    }

    @Test
    public void CreatePlaylistTest(){
        //Arrange
        String username = "testy";
        List<TrackDTO> trackList = new ArrayList<>();
        PlaylistDTO playlistDTO = new PlaylistDTO(0, "playlistName", true, trackList);
        //Act

        //Assert
        assertDoesNotThrow(() -> sut.CreatePlaylist(username, playlistDTO));
    }

    @Test
    public void DeletePlaylistTest(){
        //Arrange
        String username = "testy";
        int id = 1;
        //Act

        //Assert
        assertDoesNotThrow(() -> sut.DeletePlaylist(username, id));
    }

    @Test
    public void UpdatePlaylistTest(){
        //Arrange
        String username = "testy";
        List<TrackDTO> trackList = new ArrayList<>();
        PlaylistDTO playlistDTO = new PlaylistDTO(0, "playlistName", true, trackList);
        //Act

        //Assert
        assertDoesNotThrow(() -> sut.UpdatePlaylist(username, playlistDTO));
    }
}
