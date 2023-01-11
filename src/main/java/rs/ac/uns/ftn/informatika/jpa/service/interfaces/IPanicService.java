package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.Panic;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;

import java.util.List;
import java.util.Optional;

public interface IPanicService {

    List<Panic> getAll();

    Optional<Panic> getPanic(String id);

    void add(Panic panic);

    Panic createPanicByRide(Ride ride, String Reason);
}
