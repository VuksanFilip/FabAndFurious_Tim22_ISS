package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.Passenger;

import java.util.List;
import java.util.Optional;

public interface PassengerService {

    List<Passenger> getAll();

    Optional<Passenger> getPassenger(String id);

    void add(Passenger passenger);
}
