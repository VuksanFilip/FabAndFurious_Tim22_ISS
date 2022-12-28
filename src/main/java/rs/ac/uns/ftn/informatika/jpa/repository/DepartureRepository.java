package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.Departure;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartureRepository extends CrudRepository<Departure, Long> {

    List<Departure> findAll();
    Optional<Departure> findById(String Long);

}