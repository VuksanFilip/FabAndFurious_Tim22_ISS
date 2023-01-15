package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePanicRidePassengerDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Passenger extends User{

    @ManyToMany(mappedBy = "passengers", cascade = {CascadeType.ALL})
    @Column(name = "ride_id")
    List<Ride> rides;

    @ManyToMany(mappedBy = "passengers")
    List<FavoriteLocations> favoriteLocations;

    public Passenger() {
    }

    public Passenger(Long id, String email) {
        super(id, email);
    }

    public Passenger(String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password) {
        super(firstName, lastName, picture, phoneNumber, email, address, password);
    }

    public Passenger(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password) {
        super(id, firstName, lastName, picture, phoneNumber, email, address, password);
    }

    public Passenger(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, boolean blocked, boolean active) {
        super(id, firstName, lastName, picture, phoneNumber, email, address, password, blocked, active);
    }

    public Passenger(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, boolean blocked, boolean active, ArrayList<Ride> rides, ArrayList<FavoriteLocations> favoriteLocations) {
        super(id, firstName, lastName, picture, phoneNumber, email, address, password, blocked, active);
        this.rides = rides;
        this.favoriteLocations = favoriteLocations;
    }

    public List<Ride> getRides() {
        return rides;
    }

    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }

    public List<FavoriteLocations> getFavoriteLocations() {
        return favoriteLocations;
    }

    public void setFavoriteLocations(ArrayList<FavoriteLocations> favoriteLocations) {
        this.favoriteLocations = favoriteLocations;
    }

    public void update(Passenger passenger){
        this.setFirstName(passenger.getFirstName());
        this.setLastName(passenger.getLastName());
        this.setPicture(passenger.getPicture());
        this.setPhoneNumber(passenger.getPhoneNumber());
        this.setEmail(passenger.getEmail());
        this.setAddress(passenger.getAddress());
    }

    public void activate(){
        this.setActive(true);
    }

    public ResponsePassengerDTO parseToResponse(){
        return new ResponsePassengerDTO(this.getId(), this.getFirstName(), this.getLastName(), this.getPicture(), this.getPhoneNumber(), this.getEmail(), this.getAddress());
    }

    public ResponsePanicRidePassengerDTO parseToPanicPassengersDTO(){
        return new ResponsePanicRidePassengerDTO(this.getId(), this.getEmail());
    }
}
