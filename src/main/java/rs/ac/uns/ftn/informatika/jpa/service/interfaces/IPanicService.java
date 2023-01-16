package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.uns.ftn.informatika.jpa.model.Panic;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.model.User;

import java.util.List;
import java.util.Optional;

public interface IPanicService {

    List<Panic> getAll();

    Optional<Panic> getPanic(String id);

    void add(Panic panic);

    Panic createPanicByRide(User user, Ride ride, String Reason);

    Page<Panic> findAll(Pageable page);


}
