package nl.han.oose.nickbergen.datasource;

import nl.han.oose.nickbergen.datasource.util.DatabaseProperties;
import nl.han.oose.nickbergen.services.dto.PlaylistDTO;
import nl.han.oose.nickbergen.services.dto.PlaylistsDTO;
import nl.han.oose.nickbergen.services.dto.TrackDTO;
import nl.han.oose.nickbergen.services.exceptions.UnableToCreatePlaylistException;
import nl.han.oose.nickbergen.services.exceptions.UnableToDeletePlaylistException;
import nl.han.oose.nickbergen.services.exceptions.UnableToFetchPlaylistsWithUserException;
import nl.han.oose.nickbergen.services.exceptions.UnableToUpdatePlaylistException;

import javax.inject.Inject;
import javax.json.JsonArray;
import javax.sound.midi.Track;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlaylistDAO {
    private DatabaseProperties databaseProperties;
    private Logger logger = Logger.getLogger(getClass().getName());

    @Inject
    public void setDatabaseProperties(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    public PlaylistsDTO getPlaylistsDTOFromUser(String username) {
        List<PlaylistDTO> playlists = getAllPlaylistDTOFromUser(username);
        try {
            //Calculate total duration
            int length = 0;
            for (PlaylistDTO playlist : playlists) {
                for (TrackDTO track : playlist.getTracks()) {
                    length += track.getDuration();
                }
            }
            //Set up return DTO
            PlaylistsDTO returnList = new PlaylistsDTO(playlists, length);
            return returnList;
        } catch (Exception e) {
            throw new UnableToFetchPlaylistsWithUserException();
        }
    }

    private List<PlaylistDTO> getAllPlaylistDTOFromUser(String username) {
        List<PlaylistDTO> playlistDTOList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseProperties.getConnectionString());) {
            //Setup query
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT playlistID, name, owner" +
                            " FROM playlists"
            );
            //Get the result of the query
            ResultSet result = statement.executeQuery();
            //Loop through result and generate a list of playlistDTOs
            while (result.next()) {
                playlistDTOList.add(getPlaylistDTOInfo(
                        result.getInt("playlistID"),
                        result.getString("name"),
                        result.getString("owner"),
                        username
                ));
            }
            return playlistDTOList;
        } catch (SQLException e) {
            throw new UnableToFetchPlaylistsWithUserException();
        }
    }

    private PlaylistDTO getPlaylistDTOInfo(int id, String name, String owner, String username) {
        List<TrackDTO> trackDTOList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(databaseProperties.getConnectionString());) {
            //Setup query
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT T.trackID, T.name, T.performer, T.duration, T.album, T.playcount, T.publicationDate, T.description, T.offlineavailable" +
                            " FROM trackinplaylist AS TA" +
                            " INNER JOIN tracks AS T" +
                            " ON TA.trackID = T.trackID" +
                            " WHERE playlistID = ?"
            );
            statement.setInt(1, id);
            //Get the result of the query
            ResultSet result = statement.executeQuery();
            //Loop through the results and generate a trackDTO for every result
            while (result.next()) {

                Date publicationDate;
                publicationDate = new Date(); //QUESTION
                /*if (result.getDate("publicationDate") != null){
                    publicationDate = result.getDate("publicationDate");
                } else {
                    publicationDate = new Date();
                }*/
                trackDTOList.add(
                        new TrackDTO(
                                result.getInt("trackID"),
                                result.getString("name"),
                                result.getString("performer"),
                                result.getInt("duration"),
                                result.getString("album"),
                                result.getInt("playcount"),
                                publicationDate,
                                result.getString("description"),
                                result.getBoolean("offlineavailable")));
            }
            //Return everything as a PlaylistDTO
            return new PlaylistDTO(id, name, Objects.equals(owner, username), trackDTOList);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Unable to fetch playlists with username: " + e);
        } catch (Exception e) {
            logger.warning("Unknown exception: " + e);
        }
        return null;
    }

    public PlaylistsDTO CreatePlaylist(String username, PlaylistDTO playlistDTO){
        try (Connection connection = DriverManager.getConnection(databaseProperties.getConnectionString());) {
            //Setup query
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO playlists (name, owner) VALUES(?, ?)"
            );
            statement.setString(1, playlistDTO.getName());
            statement.setString(2, username);
            //Get the result of the query
            statement.executeQuery();
            int playlistID = getPlayListIDWithNameFromUser(playlistDTO.getName(), username);
            if (playlistID <= 0) { throw new UnableToDeletePlaylistException(); }
            //Loop through all tracks and add them to the database under the new playlist
            for (TrackDTO track : playlistDTO.getTracks()){
                PreparedStatement trackstatement = connection.prepareStatement(
                        "INSERT INTO trackinplaylist (trackID, playlistID)" +
                                " VALUES(?, ?)"
                );
                trackstatement.setInt(1, track.getId());
                trackstatement.setInt(2, playlistID);
                //Execute the query
                trackstatement.executeQuery();
            }
            //Front-end wants an updated version of all playlists as return
        } catch (SQLException e) {
            throw new UnableToCreatePlaylistException();
        }
        return  getPlaylistsDTOFromUser(username);
    }

    private int getPlayListIDWithNameFromUser(String name, String username){
        try (Connection connection = DriverManager.getConnection(databaseProperties.getConnectionString());) {
            //Setup query
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT playlistID " +
                            "FROM playlists " +
                            "WHERE name = ? " +
                            "AND owner = ?"
            );
            statement.setString(1, name);
            statement.setString(2, username);
            //Get the result of the query
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return result.getInt("playlistID");
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Unable to fetch playlistID with playlistname and username: " + e);
        }
        return -1;
    }

    public PlaylistsDTO DeletePlaylist(String username, int id) {
        try (Connection connection = DriverManager.getConnection(databaseProperties.getConnectionString());) {
            //Setup query
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM playlists " +
                            "WHERE playlistID = ?"
            );
            statement.setInt(1, id);
            //Execute the query
            statement.executeQuery();
        } catch (SQLException e) {
            throw new UnableToDeletePlaylistException();
        }
        //Front-end wants an updated version of all playlists as return
        return getPlaylistsDTOFromUser(username);
    }

    public PlaylistsDTO UpdatePlaylist(String username, PlaylistDTO playlistDTO){
        try (Connection connection = DriverManager.getConnection(databaseProperties.getConnectionString());) {
            //Setup query
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE playlists " +
                        "SET name = ? " +
                        "WHERE playlistID = ?"
            );
            statement.setString(1, playlistDTO.getName());
            statement.setInt(2, playlistDTO.getId());
            //Execute the query
            statement.executeQuery();
        } catch (SQLException e) {
            throw new UnableToUpdatePlaylistException();
        }
        //Front-end wants an updated version of all playlists as return
        return getPlaylistsDTOFromUser(username);
    }
}
