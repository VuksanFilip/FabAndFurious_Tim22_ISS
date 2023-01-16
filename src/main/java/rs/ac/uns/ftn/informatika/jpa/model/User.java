package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePanicUserDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerIdEmailDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseUserDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseUserWithIdDTO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY, generator = "ConfirmationCodeGenerator")
    @TableGenerator(table = "SEQUENCES", name = "ConfirmationCodeGenerator")
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

    public User() {
    }

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

    public User(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, boolean blocked, boolean active, String resetPasswordToken, LocalDateTime resetPasswordTokenExpiration) {
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
        this.resetPasswordToken = resetPasswordToken;
        this.resetPasswordTokenExpiration = resetPasswordTokenExpiration;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public LocalDateTime getResetPasswordTokenExpiration() {
        return resetPasswordTokenExpiration;
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
