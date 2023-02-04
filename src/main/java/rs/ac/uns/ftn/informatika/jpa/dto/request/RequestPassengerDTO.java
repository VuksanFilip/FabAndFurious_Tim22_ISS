package rs.ac.uns.ftn.informatika.jpa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPassengerDTO {

    @NotBlank(message = "Cant be empty")
    @Length(max = 50, message = "You have exceeded the allowed length")
    private String name;

    @NotBlank(message = "Can't be empty")
    @Length(max = 50, message = "You have exceeded the allowed length")
    private String surname;

    private String profilePicture;

    private String telephoneNumber;

    @Email(message = "Invalid format")
    private String email;

    private String address;

    @NotBlank(message = "Can't be empty")
    @Length(min = 8, max = 15, message = "Minimum 8, maximum 15 characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?!.*[^a-zA-Z0-9])(.{8,15})$", message = "Must contain 1 big letter")
    private String password;

    public ResponsePassengerDTO parseToResponse(Long id){
        ResponsePassengerDTO passengerResponse = new ResponsePassengerDTO(this.name, this.surname, this.profilePicture, this.telephoneNumber, this.email, this.address);
        passengerResponse.setId(id);
        return passengerResponse;
    }

    public Passenger parseToPassenger(){
        return new Passenger(this.name, this.surname, this.profilePicture, this.telephoneNumber, this.email, this.address, this.password);
    }

}
