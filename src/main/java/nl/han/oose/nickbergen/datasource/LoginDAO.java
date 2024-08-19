package nl.han.oose.nickbergen.datasource;

import nl.han.oose.nickbergen.datasource.util.DatabaseProperties;
import nl.han.oose.nickbergen.services.dto.LoginReceivedDTO;
import nl.han.oose.nickbergen.services.exceptions.InvalidLoginException;
import nl.han.oose.nickbergen.services.exceptions.SQLErrorException;

import javax.inject.Inject;
import java.sql.*;

public class LoginDAO {
    private DatabaseProperties databaseProperties;

    @Inject
    public void setDatabaseProperties(DatabaseProperties databaseProperties) {this.databaseProperties = databaseProperties;}

    public LoginDAO(){

    }

    public void CheckValidLogin(LoginReceivedDTO loginRecievedDTO){
        try (Connection connection = DriverManager.getConnection(databaseProperties.getConnectionString());) {
            //Setup query
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT username,userpassword " +
                            "FROM UserAccounts" +
                            " WHERE username = ?" +
                            " AND userpassword = ?"
            );
            statement.setString(1, loginRecievedDTO.getUser());
            statement.setString(2, loginRecievedDTO.getPassword());
            //Get the result of the query
            ResultSet resultSet = statement.executeQuery();
            //If it found one return true
            if(resultSet.next()) {
                return;
            }
        } catch (SQLException e) {
            throw new SQLErrorException();
        }
        //If not found any or caught an exception return false.
        throw new InvalidLoginException();
    }
}
