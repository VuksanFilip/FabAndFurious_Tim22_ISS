package rs.ac.uns.ftn.informatika.jpa.dummy;

import rs.ac.uns.ftn.informatika.jpa.model.Departure;
import rs.ac.uns.ftn.informatika.jpa.model.Destination;
import rs.ac.uns.ftn.informatika.jpa.model.Location;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class LocationDummy {

    public static AtomicLong locationCounter;
    public ConcurrentHashMap<Long, Location> locations;

    public LocationDummy() {
        this.locationCounter = new AtomicLong();
        this.locations = new ConcurrentHashMap<Long, Location>();
        Long id1 = Long.valueOf(1);
        Location location1 = new Location(new Departure(), new Destination());
        Long id2 = Long.valueOf(2);
        Location location2 = new Location(new Departure(), new Destination());
        this.locations.put(id1, location1);
        this.locations.put(id2, location2);
    }
}
