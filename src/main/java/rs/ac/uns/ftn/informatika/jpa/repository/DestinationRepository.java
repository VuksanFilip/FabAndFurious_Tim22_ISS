package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.Destination;

import java.util.List;
import java.util.Optional;

@Repository
public interface DestinationRepository extends CrudRepository<Destination, Long> {

    List<Destination> findAll();
    Optional<Destination> findById(String Long);

}