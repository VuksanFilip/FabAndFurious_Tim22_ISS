package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.model.Driver;

public class ResponseDriverDTO {

//    {
//        "id": 123,
//            "name": "Pera",
//            "surname": "Perić",
//            "profilePicture": "U3dhZ2dlciByb2Nrcw==",
//            "telephoneNumber": "+381123123",
//            "email": "pera.peric@email.com",
//            "address": "Bulevar Oslobodjenja 74"
//    }

    private Long id;
    private String name;
    private String surname;
    private String profilePicture;
    private String telephoneNumber;
    private String email;
    private String address;

    public ResponseDriverDTO() {
    }

    public ResponseDriverDTO(Driver driver) {
        this.id = driver.getId();
        this.name = driver.getFirstName();
        this.surname = driver.getLastName();
        this.profilePicture = driver.getPicture();
        this.telephoneNumber = driver.getPhoneNumber();
        this.email = driver.getEmail();
        this.address = driver.getAddress();
    }

    public ResponseDriverDTO(Long id, String name, String surname, String profilePicture, String telephoneNumber, String email, String address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.profilePicture = profilePicture;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.address = address;
    }

    public ResponseDriverDTO(String name, String surname, String profilePicture, String telephoneNumber, String email, String address) {
        this.id = null;
        this.name = name;
        this.surname = surname;
        this.profilePicture = profilePicture;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
