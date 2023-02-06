package rs.ac.uns.ftn.informatika.jpa.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.model.enums.RideStatus;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(locations="classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RideRepositoryTests {

    @Autowired
    private RideRepository rideRepository;

    private final Long driverId = 5L;
    private final Long wrongDriverId = 123L;
    private final Long passengerId =  2L;
    private final Long wrongPassengerId = 123L;
    private final Long rideId = 1L;
    private final Long wrongRideId = 123L;

    @Order(1)
    @Test
    @DisplayName(value = "Injected component is not null")
    public void injectedComponentsAreNotNull(){
        assertThat(this.rideRepository).isNotNull();
    }

    @Order(2)
    @Test
    @DisplayName(value = "Find ride by id")
    public void findRideByRideId() {

        Ride ride =  this.rideRepository.findById(rideId).get();
        assertNotNull(ride);
        assertEquals(ride.getId(), rideId);
    }

    @Order(3)
    @Test
    @DisplayName(value = "Dont find ride by wrong id")
    public void dontFindRideByWrongRideId() {

        boolean exist =  this.rideRepository.findById(wrongRideId).isPresent();
        assertFalse(exist);
    }

    @Order(4)
    @Test
    @DisplayName(value = "Check if ride is saved")
    public void checkIfRideIsSaved() {

        Calendar calendarForStart = Calendar.getInstance();
        calendarForStart.set(Calendar.YEAR, 2022);

        Calendar calendarForEnd = Calendar.getInstance();
        calendarForEnd.set(Calendar.YEAR, 2024);

        Date dateStart = calendarForStart.getTime();
        Date dateEnd = calendarForEnd.getTime();

        Ride ride = new Ride(dateStart, dateEnd, 1500, 15, false, false, false, RideStatus.PENDING, dateStart);

        Ride savedRide = this.rideRepository.save(ride);
        assertThat(savedRide).usingRecursiveComparison().ignoringFields("id", "driver", "passengers", "locations", "routes", "reviews", "vehicle", "letter").isEqualTo(ride);
    }

    @Order(5)
    @Test
    @DisplayName(value = "Getting all driver rides by driver id")
    public void findAllRidesByDriverIdTest(){
        Page<Ride> rides = this.rideRepository.findAllByDriverId(driverId, PageRequest.of(0, 10));

        assertTrue(rides.hasContent());
        assertEquals(rides.getSize(), 10);
    }

    @Order(6)
    @Test
    @DisplayName(value = "Getting all driver rides by driver id")
    public void findAllRidesByWrongDriverIdTest(){
        Page<Ride> rides = this.rideRepository.findAllByDriverId(wrongDriverId, PageRequest.of(0, 10));

        assertTrue(rides.isEmpty());
        assertEquals(0, rides.getNumberOfElements());
    }

    @Order(7)
    @Test
    @DisplayName(value = "Getting all driver rides between 2 dates")
    public void findAllRidesByDriverIdAndTimeOfStartAfterAndTimeOfEndBefore() {

        Calendar calendarForStart = Calendar.getInstance();
        calendarForStart.set(Calendar.YEAR, 2022);

        Calendar calendarForEnd = Calendar.getInstance();
        calendarForEnd.set(Calendar.YEAR, 2024);

        Date dateStart = calendarForStart.getTime();
        Date dateEnd = calendarForEnd.getTime();

        Page<Ride> rides = this.rideRepository.findAllByDriverIdAndTimeOfStartAfterAndTimeOfEndBefore(driverId, dateStart, dateEnd, PageRequest.of(0, 10));

        assertTrue(rides.hasContent());
        assertEquals(10, rides.getSize());
    }

    @Order(8)
    @Test
    @DisplayName(value = "Getting all driver rides between 2 wrong dates")
    public void findEmptyRidesByDriverIdAndWrongTimeOfStartAfterAndTimeOfEndBefore() {

        Calendar calendarForStart = Calendar.getInstance();
        calendarForStart.set(Calendar.YEAR, 999);

        Calendar calendarForEnd = Calendar.getInstance();
        calendarForEnd.set(Calendar.YEAR, 1000);

        Date dateStart = calendarForStart.getTime();
        Date dateEnd = calendarForEnd.getTime();

        Page<Ride> rides = this.rideRepository.findAllByDriverIdAndTimeOfStartAfterAndTimeOfEndBefore(driverId, dateStart, dateEnd, PageRequest.of(0, 10));

        assertTrue(rides.isEmpty());
        assertEquals(0, rides.getTotalElements());
    }

    @Order(9)
    @Test
    @DisplayName(value = "Getting all driver rides from start date")
    public void findAllRidesByDriverIdAndTimeOfStartAfter() {

        Calendar calendarForStart = Calendar.getInstance();
        calendarForStart.set(Calendar.YEAR, 999);

        Date dateStart = calendarForStart.getTime();

        Page<Ride> rides = this.rideRepository.findAllByDriverIdAndTimeOfStartAfter(driverId, dateStart, PageRequest.of(0, 10));

        assertTrue(rides.hasContent());
        assertEquals(10, rides.getSize());
    }

    @Order(10)
    @Test
    @DisplayName(value = "Getting empty driver rides from start date")
    public void findEmptyAllRidesByDriverIdAndWrongTimeOfStartAfter() {

        Calendar calendarForStart = Calendar.getInstance();
        calendarForStart.set(Calendar.YEAR, 9999);

        Date dateStart = calendarForStart.getTime();

        Page<Ride> rides = this.rideRepository.findAllByDriverIdAndTimeOfStartAfter(driverId, dateStart, PageRequest.of(0, 10));

        assertTrue(rides.isEmpty());
        assertEquals(0, rides.getTotalElements());
    }

    @Order(11)
    @Test
    @DisplayName(value = "Getting all driver rides before end date")
    public void findAllRidesByDriverIdAndTimeOfEndBefore() {

        Calendar calendarForEnd = Calendar.getInstance();
        calendarForEnd.set(Calendar.YEAR, 9999);

        Date dateEnd = calendarForEnd.getTime();

        Page<Ride> rides = this.rideRepository.findAllByDriverIdAndTimeOfEndBefore(driverId, dateEnd, PageRequest.of(0, 10));

        assertTrue(rides.hasContent());
        assertEquals(10, rides.getSize());
    }

    @Order(12)
    @Test
    @DisplayName(value = "Getting empty driver rides before end date")
    public void findEmptyAllRidesByDriverIdAndWrongTimeOfEndAfter() {

        Calendar calendarForEnd = Calendar.getInstance();
        calendarForEnd.set(Calendar.YEAR, 999);

        Date dateEnd = calendarForEnd.getTime();

        Page<Ride> rides = this.rideRepository.findAllByDriverIdAndTimeOfEndBefore(driverId, dateEnd, PageRequest.of(0, 10));

        assertTrue(rides.isEmpty());
        assertEquals(0, rides.getTotalElements());
    }

    @Order(13)
    @Test
    @DisplayName(value = "Getting all passenger rides")
    public void findAllRidesByPassengerId() {

        Page<Ride> rides = this.rideRepository.getRidesForPassenger(passengerId, PageRequest.of(0, 10));

        assertTrue(rides.hasContent());
        assertEquals(10, rides.getSize());
    }

    @Order(14)
    @Test
    @DisplayName(value = "Getting empty passenger rides")
    public void findWrongRidesByPassengerId() {
        Page<Ride> rides = this.rideRepository.findAllByDriverId(wrongDriverId, PageRequest.of(0, 10));

        assertTrue(rides.isEmpty());
        assertEquals(rides.getNumberOfElements(), 0);
    }

    @Order(15)
    @Test
    @DisplayName(value = "Getting passenger rides by id and with FINISHED status")
    public void findAllRidesByPassengerIdAndRideStatus(){
        List<Ride> rides = this.rideRepository.findAllRidesByPassengerIdAndRideStatus(passengerId, RideStatus.FINISHED);

        assertFalse(rides.isEmpty());
        assertEquals((rides.get(0).getStatus()), RideStatus.FINISHED);
        assertEquals((rides.get(rides.size()-1).getStatus()), RideStatus.FINISHED);
    }

    @Order(16)
    @Test
    @DisplayName(value = "Getting passenger rides by id and null status")
    public void findAllRidesByPassengerIdAndNullRideStatus(){
        List<Ride> rides = this.rideRepository.findAllRidesByPassengerIdAndRideStatus(passengerId, null);

        assertTrue(rides.isEmpty());
        assertEquals(rides.size(), 0);
    }

    @Order(17)
    @Test
    @DisplayName(value = "Getting passenger rides with wrong id and FINISHED status")
    public void findAllRidesByWrongPassengerIdAndRideStatus(){
        List<Ride> rides = this.rideRepository.findAllRidesByPassengerIdAndRideStatus(wrongPassengerId, RideStatus.FINISHED);

        assertTrue(rides.isEmpty());
        assertEquals(rides.size(), 0);
    }

    @Order(18)
    @Test
    @DisplayName(value = "Getting passenger rides with wrong id and null status")
    public void findAllRidesByWrongPassengerIdAndNullRideStatus(){
        List<Ride> rides = this.rideRepository.findAllRidesByPassengerIdAndRideStatus(wrongPassengerId, null);

        assertTrue(rides.isEmpty());
        assertEquals(rides.size(), 0);
    }

    @Order(19)
    @Test
    @DisplayName(value = "Getting ride by ride id and passenger being member of it")
    public void findRideByRideIdAndPassengerId(){
        Ride ride = this.rideRepository.findRideByRideIdAndPassengerId(rideId, passengerId);

        assertNotNull(ride);
        assertEquals(ride.getId(), rideId);
        assertTrue(ride.getPassengers().stream().filter(p -> p.getId().equals(passengerId)).findFirst().isPresent());
    }

    @Order(20)
    @Test
    @DisplayName(value = "Getting ride by ride id and passenger not being member of it")
    public void findRideByRideIdAndWrongPassengerId(){
        Ride ride = this.rideRepository.findRideByRideIdAndPassengerId(rideId, wrongPassengerId);

        assertNull(ride);
    }

    @Order(21)
    @Test
    @DisplayName(value = "Getting ride by wrong ride id and passenger being member of it")
    public void findRideByWrongRideIdAndPassengerId(){
        Ride ride = this.rideRepository.findRideByRideIdAndPassengerId(wrongRideId, passengerId);

        assertNull(ride);
    }

    @Order(22)
    @Test
    @DisplayName(value = "Getting ride by wrong ride id and passenger not being member of it")
    public void findRideByWrongRideIdAndWrongPassengerId(){
        Ride ride = this.rideRepository.findRideByRideIdAndPassengerId(wrongRideId, wrongPassengerId);

        assertNull(ride);
    }

    @Order(23)
    @Test
    @DisplayName(value = "Getting ride by ride id and driver being member of it")
    public void findRideByRideIdAndDriverId(){
        Ride ride = this.rideRepository.findRideByRideIdAndDriverId(rideId, driverId);

        assertNotNull(ride);
        assertEquals(ride.getId(), rideId);
        assertEquals(ride.getDriver().getId(), driverId);
    }

    @Order(24)
    @Test
    @DisplayName(value = "Getting ride by ride id and driver not being member of it")
    public void findRideByRideIdAndWrongDriverId(){
        Ride ride = this.rideRepository.findRideByRideIdAndDriverId(rideId, wrongDriverId);

        assertNull(ride);
    }

    @Order(25)
    @Test
    @DisplayName(value = "Getting ride by wrong ride id and driver being member of it")
    public void findRideByWrongRideIdAndDriverId(){
        Ride ride = this.rideRepository.findRideByRideIdAndDriverId(wrongRideId, driverId);

        assertNull(ride);
    }

    @Order(26)
    @Test
    @DisplayName(value = "Getting ride by wrong ride id and driver not being member of it")
    public void findRideByWrongRideIdAndWrongDriverId(){
        Ride ride = this.rideRepository.findRideByRideIdAndDriverId(wrongRideId, wrongDriverId);

        assertNull(ride);
    }
}
