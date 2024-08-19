package nl.han.oose.nickbergen.resources;

import nl.han.oose.nickbergen.datasource.LoginDAO;
import nl.han.oose.nickbergen.datasource.TokenDAO;
import nl.han.oose.nickbergen.services.dto.LoginReceivedDTO;
import nl.han.oose.nickbergen.services.dto.LoginResponseDTO;
import nl.han.oose.nickbergen.services.exceptions.InvalidLoginException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginResource {
    private LoginDAO loginDAO;
    private TokenDAO tokenDAO;

    @Inject
    public void setLoginDAO(LoginDAO loginDAO) {this.loginDAO = loginDAO;}

    @Inject
    public void setTokenDAO(TokenDAO tokenDAO) {this.tokenDAO = tokenDAO;}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response LoginAttempt(LoginReceivedDTO loginRecievedDTO){
        //Check if provided login attempt has the correct fields filled
        if (loginRecievedDTO == null || loginRecievedDTO.getUser() == null || loginRecievedDTO.getPassword() == null){
            //invalid set up for login attempt
            String returnMSG = "Bad login request.";
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(returnMSG)
                    .build();
        }
        //Verify login attempt
        loginDAO.CheckValidLogin(loginRecievedDTO);
        //Generate token for login
        String generatedToken = tokenDAO.GenerateNewToken(loginRecievedDTO.getUser());

        //Create and return success response
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(loginRecievedDTO.getUser(), generatedToken);
        return Response
                .status(Response.Status.OK)
                .entity(loginResponseDTO)
                .build();
    }
}
