package rs.ac.uns.ftn.informatika.jpa.dummy;

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
        Location location1 = new Location(id1,"Bulevar oslobodjenja 19, Novi Sad", 22.41, 35.48);
        Long id2 = Long.valueOf(2);
        Location location2 = new Location(id2, "Bulevar oslobodjenja 56, Novi Sad", 53.24, 14.96);
        this.locations.put(id1, location1);
        this.locations.put(id2, location2);
    }
}
