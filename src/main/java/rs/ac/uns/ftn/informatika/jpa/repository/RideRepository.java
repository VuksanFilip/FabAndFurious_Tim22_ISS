package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.Driver;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;

import java.util.List;
import java.util.Optional;

@Repository
public interface RideRepository extends CrudRepository<Ride, Long> {

    List<Ride> findAll();
    Optional<Ride> findById(String Long);
    long count();
    Ride save(Ride ride);
    Page<Ride> findAll(Pageable pageable);
    boolean existsById(String Long);

}