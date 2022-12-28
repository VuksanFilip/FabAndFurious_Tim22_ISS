package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.Ride;

import java.util.List;
import java.util.Optional;

public interface RideService {

    List<Ride> getAll();

    Optional<Ride> getRide(String id);

    void add(Ride ride);

    long getSize();
}
