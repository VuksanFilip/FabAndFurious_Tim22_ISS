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

    @Order(1)
    @Test
    @DisplayName(value = "Injected component is not null")
    public void injectedComponentsAreNotNull(){
        assertThat(this.rideRepository).isNotNull();
    }

    @Order(2)
    @Test
    @DisplayName(value = "Save ride")
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

    @Order(3)
    @Test
    @DisplayName(value = "Getting all driver rides by driver id")
    public void findAllRidesByDriverIdTest(){
        Page<Ride> rides = this.rideRepository.findAllByDriverId(driverId, PageRequest.of(0, 10));

        assertTrue(rides.hasContent());
        assertEquals(rides.getSize(), 10);
    }

    @Order(4)
    @Test
    @DisplayName(value = "Getting all driver rides by driver id")
    public void findAllRidesByWrongDriverIdTest(){
        Page<Ride> rides = this.rideRepository.findAllByDriverId(wrongDriverId, PageRequest.of(0, 10));

        assertTrue(rides.isEmpty());
        assertEquals(0, rides.getNumberOfElements());
    }

//    @Query("select r from Ride r, Driver d where d.id = :driverId and r.endTime <= :timeOfEnd and r.startTime >= :timeOfStart")
//    Page<Ride> findAllByDriverIdAndTimeOfStartAfterAndTimeOfEndBefore(Long driverId, Date timeOfStart, Date timeOfEnd, Pageable page);

    @Order(5)
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

    @Order(6)
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

    @Order(7)
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

    @Order(8)
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

    @Order(9)
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

    @Order(10)
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

    @Order(11)
    @Test
    @DisplayName(value = "Getting all passenger rides")
    public void findAllRidesByPassengerIdTest() {

        Page<Ride> rides = this.rideRepository.getRidesForPassenger(passengerId, PageRequest.of(0, 10));

        assertTrue(rides.hasContent());
        assertEquals(10, rides.getSize());
    }

    @Order(12)
    @Test
    @DisplayName(value = "Getting empty passenger rides")
    public void findWrongRidesByPassengerIdTest() {
        Page<Ride> rides = this.rideRepository.findAllByDriverId(wrongDriverId, PageRequest.of(0, 10));

        assertTrue(rides.isEmpty());
        assertEquals(rides.getNumberOfElements(), 0);
    }

}
