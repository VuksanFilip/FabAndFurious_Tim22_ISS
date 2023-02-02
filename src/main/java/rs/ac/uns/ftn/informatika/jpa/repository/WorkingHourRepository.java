package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.WorkingHour;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkingHourRepository extends CrudRepository<WorkingHour, Long> {

    List<WorkingHour> findAll();
    Optional<WorkingHour> findById(String Long);
    Page<WorkingHour> findAll(Pageable pageable);

    @Query("select w from WorkingHour w  where w.driver.id = :driverId")
    Page<WorkingHour> findAllByDriverId(Long driverId,
                                 Pageable page);

    @Query("select w from WorkingHour w  where w.driver.id = :driverId and w.endTime <= :timeOfEnd and w.start >= :timeOfStart")
    Page<WorkingHour> findAllByDriverIdAndTimeOfStartAfterAndTimeOfEndBefore(Long driverId,
                                                                      Date timeOfStart,
                                                                      Date timeOfEnd,
                                                                      Pageable page);

    @Query("select w from WorkingHour w where w.driver.id = :driverId and w.endTime >= :timeOfStart")
    Page<WorkingHour> findAllByDriverIdAndTimeOfStartAfter(
            Long driverId,
            Date timeOfStart,
            Pageable page);

    @Query("select w from WorkingHour w where w.driver.id = :driverId and w.endTime <= :timeOfEnd")
    Page<WorkingHour> findAllByDriverIdAndTimeOfEndBefore(
            Long driverId,
            Date timeOfEnd,
            Pageable page);
}