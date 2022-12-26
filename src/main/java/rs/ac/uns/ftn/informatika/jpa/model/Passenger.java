package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.PanicRidePassengerResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.PassengerResponseDTO;

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

    public PassengerResponseDTO parseToResponse(){
        return new PassengerResponseDTO(this.getId(), this.getFirstName(), this.getFirstName(), this.getPicture(), this.getPhoneNumber(), this.getEmail(), this.getAddress());
    }

    public PanicRidePassengerResponseDTO parseToPanicPassengersDTO(){
        return new PanicRidePassengerResponseDTO(this.getId(), this.getEmail());
    }

}
