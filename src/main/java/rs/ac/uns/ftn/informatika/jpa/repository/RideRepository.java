package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.model.enums.RideStatus;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {

    List<Ride> findAll();

    Optional<Ride> findById(String Long);

    long count();

    Ride save(Ride ride);

    Page<Ride> findAll(Pageable pageable);

    boolean existsById(String Long);

    //SKONTATI DAL JE ISTO 1
    @Query("select r from Ride r, Driver d where d.id = :driverId")
    Page<Ride> findAllByDriverId(Long driverId, Pageable pageable);

    @Query("select r from Ride r, Driver d where d.id = :driverId and r.endTime <= :timeOfEnd and r.startTime >= :timeOfStart")
    Page<Ride> findAllByDriverIdAndTimeOfStartAfterAndTimeOfEndBefore(Long driverId, Date timeOfStart, Date timeOfEnd, Pageable page);

    @Query("select r from Ride r, Driver d where d.id = :driverId and r.startTime >= :timeOfStart")
    Page<Ride> findAllByDriverIdAndTimeOfStartAfter(Long driverId, Date timeOfStart, Pageable page);

    @Query("select r from Ride r, Driver d where d.id = :driverId and r.endTime <= :timeOfEnd")
    Page<Ride> findAllByDriverIdAndTimeOfEndBefore(Long driverId, Date timeOfEnd, Pageable page);

    @Query("select r from Ride r, Passenger p where p.id = :passengerId and p member of r.passengers")
    Page<Ride> getRidesForPassenger(Long passengerId, Pageable page);

    //SKONTATI DAL JE ISTO 2
    @Query("select r from Ride r where r.driver.id = :driverId")
    Page<Ride> getRidesForDriver(Long driverId, Pageable page);

    @Query("select r from Ride r, Passenger p where p.id=:passengerId and p member of r.passengers and r.status=:rideStatus")
    List<Ride> findAllRidesByPassengerIdAndRideStatus(Long passengerId, RideStatus rideStatus);

    @Query("select r from Ride r, Passenger p where r.id = :rideId and p.id = :passengerId and p member of r.passengers")
    Ride findRideByRideIdAndPassengerId(Long rideId, Long passengerId);

    @Query("select r from Ride r where r.id = :rideId and r.driver.id = :driverId")
    Ride findRideByRideIdAndDriverId(Long rideId, Long driverId);
}