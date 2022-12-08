package rs.ac.uns.ftn.informatika.jpa.dummy;

import rs.ac.uns.ftn.informatika.jpa.model.Driver;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class DriverDummy {

    public static AtomicLong counter;
    public ConcurrentHashMap<Long, Driver> drivers;

    public DriverDummy() {
        this.counter = new AtomicLong();
        this.drivers = new ConcurrentHashMap<Long, Driver>();
    }
}
