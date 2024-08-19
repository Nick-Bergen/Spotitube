package nl.han.oose.nickbergen.datasource;

import nl.han.oose.nickbergen.datasource.util.DatabaseProperties;
import nl.han.oose.nickbergen.services.exceptions.InvalidTokenException;
import nl.han.oose.nickbergen.services.exceptions.TokenGenerationException;
import nl.han.oose.nickbergen.services.exceptions.UnableToFetchUsernameWithTokenException;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TokenDAO {
    private DatabaseProperties databaseProperties;
    private Logger logger = Logger.getLogger(getClass().getName());

    @Inject
    public void setDatabaseProperties(DatabaseProperties databaseProperties) {this.databaseProperties = databaseProperties;}

    public TokenDAO(){
    }

    public String getUserFromToken(String token){
        String username;
        if(token == null){
            throw new InvalidTokenException();
        }
        //Get username from DB
        username = getUserFromDBWithToken(token);
        if(username == null){
            throw new UnableToFetchUsernameWithTokenException();
        }
        //Return fetched username
        return username;
    }

    public String GenerateNewToken(String username){
        String generatedToken = UUID.randomUUID().toString();
        if(!InsertUserTokenCombo(username, generatedToken)){
            throw new TokenGenerationException();
        }
        return generatedToken;
    }

    private boolean InsertUserTokenCombo(String username, String token){
        try (Connection connection = DriverManager.getConnection(databaseProperties.getConnectionString());) {
            //Setup query
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO usertokens (username, token)" +
                            "VALUES (?, ?)"
            );
            statement.setString(1, username);
            statement.setString(2, token);
            //Perform insert
            statement.execute();
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "SQLException, unable to insert generated token into the Database " + e);
        }
        //failed to insert
        return false;
    }

    private String getUserFromDBWithToken(String token) {
        try (Connection connection = DriverManager.getConnection(databaseProperties.getConnectionString());) {
            //Setup query
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT username " +
                            "FROM usertokens" +
                            " WHERE token = ?"
            );
            statement.setString(1, token);
            //Get the result of the query
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getString("username");
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Unable to get username from token: " + e);
        } catch (Exception e) {
            logger.warning("Unknown exception: " + e);
        }
        return null;
    }
}
