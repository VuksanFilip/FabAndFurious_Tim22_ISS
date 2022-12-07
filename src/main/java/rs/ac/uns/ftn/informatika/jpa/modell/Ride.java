package rs.ac.uns.ftn.informatika.jpa.modell;

import java.util.ArrayList;
import java.util.Date;

public class Ride {

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


}
