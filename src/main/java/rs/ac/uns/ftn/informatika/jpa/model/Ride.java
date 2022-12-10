package rs.ac.uns.ftn.informatika.jpa.model;

import java.util.ArrayList;
import java.util.Date;

public class Ride {

    Long id;
    Date start;
    Date end;
    float price;
    Driver driver;
    ArrayList<Passenger> passengers;
    ArrayList<Path> paths;
    float avarageTime;
    ArrayList<Review> reviews;
    RejectionLetter letter;
    boolean panic;
    boolean babyFriendly;
    boolean petFriendly;
    VehicleType type;

    public Ride() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
