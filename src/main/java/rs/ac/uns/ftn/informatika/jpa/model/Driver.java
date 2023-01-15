package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePanicRideDriverDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Driver extends User{

    @OneToMany(cascade = {CascadeType.ALL})
    List<Document> documents;

    @OneToMany(cascade = {CascadeType.ALL})
    List<Ride> rides;

    @OneToOne
    Vehicle vehicle;

    public Driver() {
    }

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

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<Ride> getRides() {
        return rides;
    }

    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void update(Driver driver){
        this.setFirstName(driver.getFirstName());
        this.setLastName(driver.getLastName());
        this.setPicture(driver.getPicture());
        this.setPhoneNumber(driver.getPhoneNumber());
        this.setEmail(driver.getEmail());
        this.setAddress(driver.getAddress());
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

}
