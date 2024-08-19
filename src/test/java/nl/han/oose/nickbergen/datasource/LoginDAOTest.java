package nl.han.oose.nickbergen.datasource;

import nl.han.oose.nickbergen.datasource.util.DatabaseProperties;
import nl.han.oose.nickbergen.services.dto.LoginReceivedDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class LoginDAOTest {
    private LoginDAO sut;
    private DatabaseProperties databaseProperties;

    @BeforeEach
    void instantiate(){
        sut = new LoginDAO();

        databaseProperties = new DatabaseProperties();

        sut.setDatabaseProperties(databaseProperties);
    }

    @Test
    public void CheckLoginWithValidDTOTest(){
        //Arrange
        LoginReceivedDTO loginReceivedDTO = new LoginReceivedDTO("testy", "test");

        //Act

        //Assert
        assertDoesNotThrow(() -> sut.CheckValidLogin(loginReceivedDTO));
    }

    @Test
    public void CheckLoginWithInvalidDTOTest(){
        //Arrange
        LoginReceivedDTO loginReceivedDTO = new LoginReceivedDTO("fakeuser", "password123");

        //Act

        //Assert
        assertThrows(RuntimeException.class, () -> sut.CheckValidLogin(loginReceivedDTO));
    }
}
