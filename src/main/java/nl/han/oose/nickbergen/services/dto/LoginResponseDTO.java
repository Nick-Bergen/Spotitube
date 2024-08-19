package nl.han.oose.nickbergen.services.dto;

public class LoginResponseDTO {
    private String token;
    private String user;

    public LoginResponseDTO(){}

    public LoginResponseDTO(String username, String token){
        this.token = token;
        this.user = username;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
