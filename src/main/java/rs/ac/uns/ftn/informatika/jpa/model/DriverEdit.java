package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestEditDriverDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class DriverEdit {
//    {
//        "name": "Pera",
//            "surname": "Perić",
//            "profilePicture": "U3dhZ2dlciByb2Nrcw==",
//            "telephoneNumber": "+381123123",
//            "email": "pera.peric@email.com",
//            "address": "Bulevar Oslobodjenja 74"
//    }
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String profilePicture;
    private String telephoneNumber;
    private String email;
    private String address;

    public DriverEdit(String name, String surname, String profilePicture, String telephoneNumber, String email, String address) {
        this.name = name;
        this.surname = surname;
        this.profilePicture = profilePicture;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.address = address;
    }

    public RequestEditDriverDTO parseToDTO() {
        return new RequestEditDriverDTO(this.name, this.surname, this.profilePicture, this.telephoneNumber, this.email, this.address);
    }
}
