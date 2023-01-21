package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePanicUserDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseUserDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseUserWithIdDTO;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String picture;
    private String phoneNumber;
    private String email;
    private String address;
    private String password;
    private boolean blocked;
    private boolean active;
    private String resetPasswordToken;
    private LocalDateTime resetPasswordTokenExpiration;

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public User(String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, boolean blocked, boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.picture = picture;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.password = password;
        this.blocked = blocked;
        this.active = active;
    }

    public User(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, boolean blocked, boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.picture = picture;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.password = password;
        this.blocked = blocked;
        this.active = active;
    }

    public User(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.picture = picture;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.password = password;
        this.blocked = false;
        this.active = false;
    }

    public User(String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.picture = picture;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.password = password;
    }

    public void setResetPasswordTokenExpiration(LocalDateTime resetPasswordTokenExpiration) {
        this.resetPasswordTokenExpiration = resetPasswordTokenExpiration;
    }

    public ResponseUserDTO parseToResponseUser(){
        return new ResponseUserDTO(this.firstName, this.lastName, this.picture, this.phoneNumber, this.email, this.address);
    }

    public ResponseUserWithIdDTO parseToResponseUserWithId(){
        return new ResponseUserWithIdDTO(this.id, this.firstName, this.lastName, this.picture, this.phoneNumber, this.email, this.address);
    }

    public ResponsePanicUserDTO parseToPanicResponse(){
        return new ResponsePanicUserDTO(this.firstName, this.lastName, this.picture, this.phoneNumber, this.email, this.address);
    }

}

