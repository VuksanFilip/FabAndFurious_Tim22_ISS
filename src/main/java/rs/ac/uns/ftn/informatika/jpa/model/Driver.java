package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePanicRideDriverDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Driver{

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

    @OneToMany(cascade = {CascadeType.ALL})
    List<Document> documents;

    @OneToMany(cascade = {CascadeType.ALL})
    List<Ride> rides;

    @OneToOne
    Vehicle vehicle;

    public Driver() {
    }

    public Driver(Long id) {
        this.id = id;
    }

    public Driver(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Driver(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, boolean blocked, boolean active) {
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

    public Driver(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password){
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

    public Driver( String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password){
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

    public Driver(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, boolean blocked, boolean active, ArrayList<Document> documents, ArrayList<Ride> rides, Vehicle vehicle) {
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
        this.documents = documents;
        this.rides = rides;
        this.vehicle = vehicle;
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
        this.firstName = driver.getFirstName();
        this.lastName = driver.getLastName();
        this.picture = driver.getPicture();
        this.phoneNumber = driver.getPhoneNumber();
        this.email = driver.getEmail();
        this.address = driver.getAddress();
    }

    public ResponseDriverDTO parseToResponse(){
        return new ResponseDriverDTO(this.getId(), this.getFirstName(), this.getLastName(), this.getPicture(), this.getPhoneNumber(), this.getEmail(), this.getAddress());
    }

    public ResponsePanicRideDriverDTO parseToPanicDriverResponse(){
        return new ResponsePanicRideDriverDTO(this.getId(), this.getEmail());
    }
}
