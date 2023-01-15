package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.Driver;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends CrudRepository<Driver, Long> {

    List<Driver> findAll();
    Optional<Driver> findById(String Long);
    Page<Driver> findAll(Pageable pageable);
    Driver findByEmail(String email);
    boolean existsById(String Long);
}