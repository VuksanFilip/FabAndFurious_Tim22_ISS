package rs.ac.uns.ftn.informatika.jpa.dto.request;

import org.hibernate.validator.constraints.Length;
import rs.ac.uns.ftn.informatika.jpa.model.Driver;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class RequestEditDriverDTO {

    @Length(max = 50, message = "You have exceeded the allowed length (50)")
    private String name;

    @Length(max = 50, message = "You have exceeded the allowed length (50)")
    private String surname;

    private String profilePicture;

    //@Pattern(regexp = "^(?=.*[0-9])(?!.*[^0-9])(.{5,50})$", message = "Must contain only numbers (MIN 5, MAX 50)")
    private String telephoneNumber;

   // @Email(message = "Invalid format")
    private String email;

   // @NotBlank(message = "{required}")
   // @Length(max = 100, message = "Minimum 8, maximum 15 characters")
    private String address;

    public RequestEditDriverDTO() {
    }

    public RequestEditDriverDTO(String name, String surname, String profilePicture, String telephoneNumber, String email, String address) {
        this.name = name;
        this.surname = surname;
        this.profilePicture = profilePicture;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.address = address;
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

    public Driver parseToDriver() {
        return new Driver(this.name, this.surname, this.profilePicture, this.telephoneNumber, this.email, this.address, false, false);
    }
}
