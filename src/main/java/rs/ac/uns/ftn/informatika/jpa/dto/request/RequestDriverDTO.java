package rs.ac.uns.ftn.informatika.jpa.dto.request;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Driver;

public class RequestDriverDTO {

//    {
//        "name": "Pera",
//            "surname": "Perić",
//            "profilePicture": "U3dhZ2dlciByb2Nrcw==",
//            "telephoneNumber": "+381123123",
//            "email": "pera.peric@email.com",
//            "address": "Bulevar Oslobodjenja 74",
//            "password": "Pasword123"
//    }

    private String name;
    private String surname;
    private String profilePicture;
    private String telephoneNumber;
    private String email;
    private String address;
    private String password;

    public RequestDriverDTO() {
    }

    public RequestDriverDTO(String name, String surname, String profilePicture, String telephoneNumber, String email, String address, String password) {
        this.name = name;
        this.surname = surname;
        this.profilePicture = profilePicture;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.address = address;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ResponseDriverDTO parseToResponse(Long id){
        ResponseDriverDTO driverResponse = new ResponseDriverDTO(this.name, this.surname, this.profilePicture, this.telephoneNumber, this.email, this.address);
        driverResponse.setId(id);
        return driverResponse;
    }

    public Driver parseToDriver(){
        return new Driver(this.name, this.surname, this.profilePicture, this.telephoneNumber, this.email, this.address, this.password);
    }
}
