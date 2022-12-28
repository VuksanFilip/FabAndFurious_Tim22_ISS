package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.Destination;

import java.util.List;
import java.util.Optional;

public interface DestinationService {

    List<Destination> getAll();

    Optional<Destination> getDestination(String id);

    void add(Destination destination);
}
