package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
import rs.ac.uns.ftn.informatika.jpa.model.RejectionLetter;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.model.RideStatus;

import java.util.List;
import java.util.Optional;

public interface RideService {

    List<Ride> getAll();

    Optional<Ride> getRide(String id);

    Ride updateRide(Ride ride);

    void add(Ride ride);

    long getSize();

    Ride getRideByDriverId(String id);

    Ride getRideByPassengerId(String id);

    void updateRideByStatus(String id, RideStatus status);

    void updateRideByRejectionLetter(String id, RejectionLetter letter);

    Page<Ride> findAll(Pageable page);

}
