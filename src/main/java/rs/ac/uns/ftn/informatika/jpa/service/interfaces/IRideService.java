package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestRideDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseReportDayDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseRideNoStatusDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Driver;
import rs.ac.uns.ftn.informatika.jpa.model.RejectionLetter;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.model.enums.RideStatus;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IRideService {

    List<Ride> getAll();

    Optional<Ride> getRide(String id);

    boolean existsById(String id);

    Ride updateRide(Ride ride);

    void add(Ride ride);

    long getSize();

    Ride getaActiveRideByDriverId(String id);

    Ride getActiveRideByPassengerId(String id);

    void updateRideByStatus(String id, RideStatus status);

    void updateRideByRejectionLetter(String id, RejectionLetter letter);

    Page<Ride> findAll(Pageable page);

    List<Ride> getRidesOfDriver(Driver driver);

    boolean checkIfDriverHasPandingRides(Driver driver);

    Ride parseToRide(RequestRideDTO requestRideDTO, Driver driver);

    boolean checkIfNotPendingAndNotStartedById(String id);

    boolean checkIfNotAcceptedById(String id);

    boolean checkIfNotPendingById(String id);

    boolean checkIfNotStartedById(String id);

    boolean checkIfNotPendingAndNotAcceptedById(String id);

    void updateRideByRejectionLetterAndStatus(String id, RejectionLetter rejectionLetter, RideStatus rideStatus);

    List<ResponseRideNoStatusDTO> getPageableResponseRide(Pageable page, String id);

    List<ResponseRideNoStatusDTO> getResponseRide(String id);
    List<ResponseRideNoStatusDTO> getResponseRideDriver(String id);
    List<ResponseRideNoStatusDTO> getResponseRidePendingDriver(String id);

    int getNumberOfRidesForPessanger(String id);

    Page<Ride> findAll(String id, Pageable page, Date from, Date to);
    
    List<Ride> getUserRidesBetweenDates(List<Ride> allRides, String from, String to);

    List<ResponseReportDayDTO> countRidesForDay(List<Ride> rides, String from, String to);

    int getSumReport(List<ResponseReportDayDTO> dates);

    float getAverageReport(List<ResponseReportDayDTO> dates);

    List<ResponseReportDayDTO> countMoneyForDay(List<Ride> rides, String from, String to);

    List<ResponseReportDayDTO> countKmsForDay(List<Ride> rides, String from, String to);

    Page<Ride> getRidesForPassenger(String passengerId, Pageable page);

    Page<Ride> getRidesForDriver(String driverId, Pageable page);

    List<Ride> findAllRidesByPassengerIdAndRideStatus(String passengerId, RideStatus rideStatus);

    boolean checkIfPassengerExistInRide(String rideId, String passengerId);

    boolean checkIfDriverExistInRide(String rideId, String driverId);
}
