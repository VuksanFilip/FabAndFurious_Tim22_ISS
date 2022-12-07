package rs.ac.uns.ftn.informatika.jpa.modell;

import java.util.ArrayList;

public class Passenger extends User {

//    ArrayList<Ride> rides;
//    ArrayList<Path> favouritePaths;

    public Passenger() {
    }

    public Passenger(String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, boolean blocked, boolean active) {
        super(firstName, lastName, picture, phoneNumber, email, address, password, blocked, active);
    }

    public Passenger(String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, boolean blocked, boolean active, ArrayList<Ride> rides, ArrayList<Path> favouritePaths) {
        super(firstName, lastName, picture, phoneNumber, email, address, password, blocked, active);
//        this.rides = rides;
//        this.favouritePaths = favouritePaths;
    }

//    public ArrayList<Ride> getRides() {
//        return rides;
//    }
//
//    public void setRides(ArrayList<Ride> rides) {
//        this.rides = rides;
//    }
//
//    public ArrayList<Path> getFavouritePaths() {
//        return favouritePaths;
//    }
//
//    public void setFavouritePaths(ArrayList<Path> favouritePaths) {
//        this.favouritePaths = favouritePaths;
//    }
}
