package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.Location;

import java.util.List;
import java.util.Optional;

public interface ILocationService {

    List<Location> getAll();

    Optional<Location> getLocation(String id);

    void add(Location location);
}
