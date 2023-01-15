package rs.ac.uns.ftn.informatika.jpa.dto.request;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseLoginDTO;

public class RequestLoginDTO {

    private String email;
    private String password;

    public RequestLoginDTO() {
    }

    public RequestLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ResponseLoginDTO parseToResponse(){
        return new ResponseLoginDTO("accessToken", "refreshToken");
    }
}
