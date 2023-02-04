package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.DriverEdit;

@Repository
public interface DriverEditRepository extends CrudRepository<DriverEdit, Long> {
}
