package rs.ac.uns.ftn.informatika.jpa.dummy;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;

public class PassengerDummy {

    public static AtomicLong passengerCounter;
    public ConcurrentHashMap<Long, Passenger> passengers;

    public PassengerDummy() {
        this.passengerCounter = new AtomicLong();
        this.passengers = new ConcurrentHashMap<Long, Passenger>();
    }
}
