package rs.ac.uns.ftn.informatika.jpa.dummy;

import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
import rs.ac.uns.ftn.informatika.jpa.model.Review;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ReviewDummy {

    public static AtomicLong counter;
    public ConcurrentHashMap<Long, Review> reviewsForDrivers;
    public ConcurrentHashMap<Long, Review> reviewsForVehicles;

    public ReviewDummy() {
        this.counter = new AtomicLong();
        this.reviewsForDrivers = new ConcurrentHashMap<Long, Review>();
        this.reviewsForVehicles = new ConcurrentHashMap<Long, Review>();
    }
}
