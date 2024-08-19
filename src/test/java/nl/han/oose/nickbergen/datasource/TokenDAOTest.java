package nl.han.oose.nickbergen.datasource;

import nl.han.oose.nickbergen.datasource.util.DatabaseProperties;
import nl.han.oose.nickbergen.services.exceptions.InvalidTokenException;
import nl.han.oose.nickbergen.services.exceptions.TokenGenerationException;
import nl.han.oose.nickbergen.services.exceptions.UnableToFetchUsernameWithTokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TokenDAOTest {
    private TokenDAO sut;
    private DatabaseProperties databaseProperties;

    @BeforeEach
    void instantiate(){
        sut = new TokenDAO();

        databaseProperties = new DatabaseProperties();

        sut.setDatabaseProperties(databaseProperties);
    }

    @Test
    public void getValidUserFromTokenTest(){
        //Arrange
        String token = "0005406a-ea59-4cca-b54d-dd5002457ce2";
        //Act
        String returnString = sut.getUserFromToken(token);

        //Assert
        assertEquals("testy", returnString);
    }

    @Test
    public void getUserFromTokenInvalidTokenExceptionTest(){
        //Arrange
        String token = null;
        //Act

        //Assert
        assertThrows(InvalidTokenException.class, () -> sut.getUserFromToken(token));
    }

    @Test
    public void getUserFromTokenUnableToFetchUsernameWithTokenExceptionTest(){
        //Arrange
        String token = "non-existing token";
        //Act

        //Assert
        assertThrows(UnableToFetchUsernameWithTokenException.class, () -> sut.getUserFromToken(token));
    }

    @Test
    public void GenerateNewTokenTest(){
        //Can be done better by mocking the generator
        //Arrange
        String username = "testy";
        //Act

        //Assert
        assertDoesNotThrow(() -> sut.GenerateNewToken(username));
    }

    @Test
    public void GenerateNewTokenTokenGenerationExceptionTest(){
        //Can be done better by mocking the generator
        //Arrange
        String username = null;
        //Act

        //Assert
        assertThrows(TokenGenerationException.class, () -> sut.GenerateNewToken(username));
    }

    @Test
    public void InsertUserTokenComboTest(){
        //Can be done better by mocking the generator
        //Arrange
        String username = "testy";
        //Act

        //Assert
        assertDoesNotThrow(() -> sut.GenerateNewToken(username));
    }
}
