package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePanicRidePassengerDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerIdEmailDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Passenger extends User{

    @ManyToMany(mappedBy = "passengers", cascade = {CascadeType.ALL})
    @Column(name = "ride_id")
    private List<Ride> rides;

    @ManyToMany(mappedBy = "passengers")
    private List<FavoriteRoute> favoriteRoutes;

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

    public Passenger(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, boolean blocked, boolean active, ArrayList<Ride> rides, ArrayList<FavoriteRoute> favoriteRoutes) {
        super(id, firstName, lastName, picture, phoneNumber, email, address, password, blocked, active);
        this.rides = rides;
        this.favoriteRoutes = favoriteRoutes;
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

    public ResponsePassengerIdEmailDTO parseToResponseIdEmail() {
        return new ResponsePassengerIdEmailDTO(this.getId(), this.getEmail());
    }
}
