package rs.ac.uns.ftn.informatika.jpa.dummy;

import rs.ac.uns.ftn.informatika.jpa.model.Document;
import rs.ac.uns.ftn.informatika.jpa.model.Vehicle;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class VehicleDummy {
    public static AtomicLong counter;
    public ConcurrentHashMap<Long, Vehicle> vehicles;

    public VehicleDummy() {
        this.counter = new AtomicLong();
        this.vehicles = new ConcurrentHashMap<Long, Vehicle>();
    }
}
