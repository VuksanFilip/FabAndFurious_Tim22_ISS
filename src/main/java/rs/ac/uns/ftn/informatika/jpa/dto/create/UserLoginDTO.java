package rs.ac.uns.ftn.informatika.jpa.dto.create;

import rs.ac.uns.ftn.informatika.jpa.dto.response.LoginResponseDTO;

public class UserLoginDTO {

//    {
//        "email": "pera.peric@email.com",
//            "password": "sifra123"
//    }

    private String email;
    private String password;

    public UserLoginDTO() {
    }

    public UserLoginDTO(String email, String password) {
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

    public LoginResponseDTO parseToResponse(){
        return new LoginResponseDTO("accessToken", "refreshToken");
    }
}
