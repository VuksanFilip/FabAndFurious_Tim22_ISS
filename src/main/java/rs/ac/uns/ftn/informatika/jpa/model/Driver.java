package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestDriverUpdateDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestPassengerUpdateDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverIdEmailDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePanicRideDriverDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Driver extends User{

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "driver", fetch = FetchType.LAZY)
    private List<Document> documents;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Ride> rides;

    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Vehicle vehicle;

    @OneToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private DriverEdit edit;

    public Driver(Long id) {
        super(id);
    }

    public Driver(Long id, String email) {
        super(id, email);
    }

    public Driver(String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, boolean blocked, boolean active){
        super(firstName, lastName, picture, phoneNumber, email, address, password, blocked, active);
    }

    public Driver(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, boolean blocked, boolean active) {
        super(id, firstName, lastName, picture, phoneNumber, email, address, password, blocked, active);
    }

    public Driver(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, boolean blocked, boolean active, ArrayList<Document> documents, ArrayList<Ride> rides, Vehicle vehicle) {
        super(id, firstName, lastName, picture, phoneNumber, email, address, password, blocked, active);
        this.documents = documents;
        this.rides = rides;
        this.vehicle = vehicle;
    }

    public Driver(String name, String surname, String profilePicture, String telephoneNumber, String email, String address, boolean b, boolean b1) {
        super(name, surname, profilePicture, telephoneNumber, email, address, b, b1);
    }
    public void update2(RequestDriverUpdateDTO driverUpdateDTO){
        if(!Objects.equals(driverUpdateDTO.getName(), "")){
            this.setFirstName(driverUpdateDTO.getName());
        }
        if(!Objects.equals(driverUpdateDTO.getSurname(), "")){
            this.setLastName(driverUpdateDTO.getSurname());
        }
        if(!Objects.equals(driverUpdateDTO.getProfilePicture(), "")){
            this.setPicture(driverUpdateDTO.getProfilePicture());
        }
        if(!Objects.equals(driverUpdateDTO.getTelephoneNumber(), "")){
            this.setPhoneNumber(driverUpdateDTO.getTelephoneNumber());
        }
        if(!Objects.equals(driverUpdateDTO.getEmail(), "")){
            this.setEmail(driverUpdateDTO.getEmail());
        }
        if(!Objects.equals(driverUpdateDTO.getAddress(), "")){
            this.setAddress(driverUpdateDTO.getAddress());
        }
    }


    public void update(){
        if(this.edit.getName() != null){
            this.setFirstName(this.edit.getName());
        }
        if(this.edit.getSurname() != null){
            this.setLastName(this.edit.getSurname());
        }
        if(this.edit.getProfilePicture() != null){
            this.setPicture(this.edit.getProfilePicture());
        }
        if(this.edit.getTelephoneNumber() != null){
            this.setPhoneNumber(this.edit.getTelephoneNumber());
        }
        if(this.edit.getEmail() != null){
            this.setEmail(this.edit.getEmail());
        }
        if(this.edit.getAddress() != null){
            this.setAddress(this.edit.getAddress());
        }
    }

    public void addVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
    }

    public ResponseDriverDTO parseToResponse(){
        return new ResponseDriverDTO(this.getId(), this.getFirstName(), this.getLastName(), this.getPicture(), this.getPhoneNumber(), this.getEmail(), this.getAddress());
    }

    public ResponsePanicRideDriverDTO parseToPanicDriverResponse(){
        return new ResponsePanicRideDriverDTO(this.getId(), this.getEmail());
    }

    public ResponseDriverIdEmailDTO parseToResponseIdEmail() {
        return new ResponseDriverIdEmailDTO(this.getId(), this.getEmail());
    }
}
