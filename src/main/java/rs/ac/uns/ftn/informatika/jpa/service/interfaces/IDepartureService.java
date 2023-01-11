package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.Departure;

import java.util.List;
import java.util.Optional;

public interface IDepartureService {

    List<Departure> getAll();

    Optional<Departure> getDeparture(String id);

    void add(Departure departure);
}
