package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.WorkHour;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkHourRepository extends CrudRepository<WorkHour, Long> {

    List<WorkHour> findAll();
    Optional<WorkHour> findById(String Long);

}