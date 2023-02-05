package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestPassengerUpdateDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePanicRidePassengerDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerIdEmailDTO;

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
public class Passenger extends User{

    @ManyToMany(mappedBy = "passengers", cascade = {CascadeType.ALL})
    @Column(name = "ride_id")
    private List<Ride> rides;

    @ManyToMany(mappedBy = "passengers")
    private List<FavoriteRoutes> favoriteRoutes;

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

    public Passenger(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, boolean blocked, boolean active, ArrayList<Ride> rides, List<FavoriteRoutes> favoriteRoutes) {
        super(id, firstName, lastName, picture, phoneNumber, email, address, password, blocked, active);
        this.rides = rides;
        this.favoriteRoutes = favoriteRoutes;
    }

    public void update(RequestPassengerUpdateDTO passenger){
        if(!Objects.equals(passenger.getName(), "")){
            this.setFirstName(passenger.getName());
        }
        if(!Objects.equals(passenger.getSurname(), "")){
            this.setLastName(passenger.getSurname());
        }
        if(!Objects.equals(passenger.getProfilePicture(), "")){
            this.setPicture(passenger.getProfilePicture());
        }
        if(!Objects.equals(passenger.getTelephoneNumber(), "")){
            this.setPhoneNumber(passenger.getTelephoneNumber());
        }
        if(!Objects.equals(passenger.getEmail(), "")){
            this.setEmail(passenger.getEmail());
        }
        if(!Objects.equals(passenger.getAddress(), "")){
            this.setAddress(passenger.getAddress());
        }
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
