package rs.ac.uns.ftn.informatika.jpa.dummy;

import rs.ac.uns.ftn.informatika.jpa.model.Ride;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class RideDummy {

    public static AtomicLong rideCounter;
    public ConcurrentHashMap<Long, Ride> rides;

    public RideDummy() {
        this.rideCounter = new AtomicLong();
        this.rides = new ConcurrentHashMap<Long, Ride>();
    }
}
