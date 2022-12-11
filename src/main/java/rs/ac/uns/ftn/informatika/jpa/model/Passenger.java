package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.PassengerResponseDTO;

import java.util.ArrayList;

public class Passenger extends User{

    ArrayList<Ride> rides;
    ArrayList<Path> favoritePaths;

    public Passenger() {
    }

    public Passenger(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password) {
        super(id, firstName, lastName, picture, phoneNumber, email, address, password);
        this.rides = new ArrayList<Ride>();
        this.favoritePaths = new ArrayList<Path>();
    }

    public ArrayList<Ride> getRides() {
        return rides;
    }

    public void setRides(ArrayList<Ride> rides) {
        this.rides = rides;
    }

    public ArrayList<Path> getFavoriteRides() {
        return favoritePaths;
    }

    public void setFavoriteRides(ArrayList<Path> favoriteRides) {
        this.favoritePaths = favoriteRides;
    }

        public PassengerResponseDTO parseToResponse(){
        return new PassengerResponseDTO(this.getId(), this.getFirstName(), this.getFirstName(), this.getPicture(), this.getPhoneNumber(), this.getEmail(), this.getAddress());
    }

}
