package nl.han.oose.nickbergen.services.dto;

public class LoginReceivedDTO {
    private String user;
    private String password;

    public LoginReceivedDTO() {
    }
    public LoginReceivedDTO(String username, String password){
        this.user = username;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
