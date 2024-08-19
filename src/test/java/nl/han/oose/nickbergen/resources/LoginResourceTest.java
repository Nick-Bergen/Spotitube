package nl.han.oose.nickbergen.resources;

import nl.han.oose.nickbergen.datasource.LoginDAO;
import nl.han.oose.nickbergen.datasource.TokenDAO;
import nl.han.oose.nickbergen.services.dto.LoginReceivedDTO;
import nl.han.oose.nickbergen.services.dto.LoginResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginResourceTest {
    private LoginResource sut;
    private LoginDAO loginDAO;
    private TokenDAO tokenDAO;

    @BeforeEach
    void instantiate(){
        sut = new LoginResource();

        loginDAO = mock(LoginDAO.class);
        tokenDAO = mock(TokenDAO.class);

        sut.setLoginDAO(loginDAO);
        sut.setTokenDAO(tokenDAO);
    }

    @Test
    public void LoginAttemptWithValidLoginTest(){
        //Arrange
        LoginReceivedDTO loginRecievedDTO = new LoginReceivedDTO("testy", "test");

        //Act
        Response testResponse = sut.LoginAttempt(loginRecievedDTO);

        //Assert
        verify(loginDAO).CheckValidLogin(loginRecievedDTO);
    }

    @Test
    public void LoginAttemptReturnsValidResponseTest(){
        //Arrange
        LoginReceivedDTO loginRecievedDTO = new LoginReceivedDTO("testy", "test");

        //Act
        Response testResponse = sut.LoginAttempt(loginRecievedDTO);

        // Assert.
        assertTrue(testResponse.getEntity() instanceof LoginResponseDTO);
    }

    @Test
    public void LoginAttemptWithInvalidFieldsTest(){
        //Arrange
        LoginReceivedDTO loginRecievedDTO = new LoginReceivedDTO();

        //Act
        Response testResponse = sut.LoginAttempt(loginRecievedDTO);

        // Assert.
        assertEquals(Response.Status.BAD_REQUEST, testResponse.getStatusInfo());
    }
}
