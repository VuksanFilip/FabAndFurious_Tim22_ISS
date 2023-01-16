package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.WorkingHour;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkingHourRepository extends CrudRepository<WorkingHour, Long> {

    List<WorkingHour> findAll();
    Optional<WorkingHour> findById(String Long);

}