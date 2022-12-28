package rs.ac.uns.ftn.informatika.jpa.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends CrudRepository<Passenger, Long> {

    List<Passenger> findAll();
    Optional<Passenger> findById(String Long);

}
