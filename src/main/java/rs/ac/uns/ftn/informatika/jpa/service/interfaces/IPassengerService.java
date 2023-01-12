package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;

import java.util.List;
import java.util.Optional;

public interface IPassengerService {

    List<Passenger> getAll();

    Optional<Passenger> getPassenger(String id);

    void add(Passenger passenger);

    Page<Passenger> findAll(Pageable page);

    Passenger findByEmail(String email);

}
