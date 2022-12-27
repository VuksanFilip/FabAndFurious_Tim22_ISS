package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePanicRidePassengerDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerDTO;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Passenger extends User{

    @ManyToMany
    List<Ride> rides;

    @OneToMany
    List<Path> favoritePaths;

    public Passenger() {
    }

    public Passenger(Long id, String email) {
        super(id, email);
    }

    public Passenger(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password) {
        super(id, firstName, lastName, picture, phoneNumber, email, address, password);
        this.rides = new ArrayList<Ride>();
        this.favoritePaths = new ArrayList<Path>();
    }

    public List<Ride> getRides() {
        return rides;
    }

    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }

    public List<Path> getFavoriteRides() {
        return favoritePaths;
    }

    public void setFavoriteRides(ArrayList<Path> favoriteRides) {
        this.favoritePaths = favoriteRides;
    }

    public ResponsePassengerDTO parseToResponse(){
        return new ResponsePassengerDTO(this.getId(), this.getFirstName(), this.getFirstName(), this.getPicture(), this.getPhoneNumber(), this.getEmail(), this.getAddress());
    }

    public ResponsePanicRidePassengerDTO parseToPanicPassengersDTO(){
        return new ResponsePanicRidePassengerDTO(this.getId(), this.getEmail());
    }

}
