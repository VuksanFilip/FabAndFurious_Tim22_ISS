package rs.ac.uns.ftn.informatika.jpa.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLocationWithAddressDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestRideDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerIdEmailDTO;
import rs.ac.uns.ftn.informatika.jpa.model.enums.RideStatus;
import rs.ac.uns.ftn.informatika.jpa.repository.RideRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import rs.ac.uns.ftn.informatika.jpa.model.*;

@SpringBootTest
public class RideServiceTests {
    @Autowired
    private RideServiceImpl rideService;

    @MockBean
    private RideRepository rideRepository;

    @MockBean
    private IPassengerService passengerService;

    @MockBean
    private ILocationService locationService;

    @MockBean
    private IRouteService routeService;

    @MockBean
    private IDriverService driverService;

    @MockBean
    private IVehicleTypeService vehicleTypeService;

    @Test
    public void shouldReturnAllRides(){
        int numberOfRides = 5;
        Mockito.when(this.rideRepository.findAll()).thenReturn(Arrays.asList(new Ride(), new Ride(), new Ride(), new Ride(), new Ride()));
        int actualNumberOfRides = rideService.getAll().size();
        assertEquals(numberOfRides, actualNumberOfRides);
    }

    @Test
    public void shouldFindRide(){
        Ride mockRide = new Ride();
        mockRide.setId(1L);
        Mockito.when(rideRepository.findById(mockRide.getId())).thenReturn(Optional.of(mockRide));
        Ride actualRide = rideService.getRide(mockRide.getId().toString()).get();
        assertEquals(mockRide.getId(), actualRide.getId());
    }

    @Test
    public void shouldNotFindRide(){
        Long rideId = 123L;
        Mockito.when(rideRepository.findById(rideId)).thenReturn(Optional.empty());
        assertEquals(rideService.getRide(rideId.toString()), Optional.empty());
    }

    @Test
    public void shouldExistRide(){
        Ride mockRide = new Ride();
        mockRide.setId(1L);
        boolean mockAnswer = true;
        Mockito.when(rideRepository.existsById(mockRide.getId())).thenReturn(mockAnswer);
        boolean actualAnswer = rideService.existsById("1");
        assertEquals(actualAnswer, mockAnswer);
    }

    @Test
    public void shouldNotExistRide(){
        Ride mockRide = new Ride();
        mockRide.setId(123L);
        boolean mockAnswer = false;
        Mockito.when(rideRepository.existsById(mockRide.getId())).thenReturn(mockAnswer);
        boolean actualAnswer = rideService.existsById("123");
        assertEquals(actualAnswer, mockAnswer);
    }

    @Test
    public void shouldUpdateRide(){
        Ride mockRide = new Ride();
        mockRide.setId(10L);
        Mockito.when(rideRepository.save(mockRide)).thenReturn(mockRide);
        Ride actualRide = rideService.updateRide(mockRide);
        assertEquals(actualRide, mockRide);
    }

    @Test
    public void shouldAddRide(){
        Ride mockRide = new Ride();
        mockRide.setId(10L);
        Mockito.when(rideRepository.save(mockRide)).thenReturn(mockRide);
        rideService.add(mockRide);
    }

    @Test
    public void shouldReturnNumberOfRides(){
        Long expectedNumber = 4L;
        Mockito.when(rideRepository.count()).thenReturn(expectedNumber);
        Long actualNumber = rideService.getSize();
        assertEquals(actualNumber, expectedNumber);
    }

    @Test
    public void shouldReturnAllRidesByPassengerIdAndRideStatus(){
        Long passengerId = 2L;
        Mockito.when(rideRepository.findAllRidesByPassengerIdAndRideStatus(passengerId, RideStatus.FINISHED)).thenReturn(Arrays.asList(new Ride()));
        assertEquals(rideService.findAllRidesByPassengerIdAndRideStatus("2", RideStatus.FINISHED).size(), 1);
    }

    @Test
    public void shouldReturnTrueForExistingPassenger(){
        Ride mockRide = new Ride();
        mockRide.setId(1L);
        List<Passenger> mockList = new ArrayList<>();
        Passenger mockPassenger = new Passenger();
        mockPassenger.setId(1L);
        mockList.add(mockPassenger);
        mockRide.setPassengers(mockList);
        Mockito.when(rideRepository.findRideByRideIdAndPassengerId(mockRide.getId(), mockPassenger.getId())).thenReturn(mockRide);
        boolean expectedAnswer = true;
        boolean actualAnswer = rideService.checkIfPassengerExistInRide(mockRide.getId().toString(), mockPassenger.getId().toString());
        assertEquals(actualAnswer, expectedAnswer);
    }

    @Test
    public void shouldReturnFalseForNonExistingPassenger(){
        Ride mockRide = new Ride();
        mockRide.setId(1L);
        Passenger mockPassenger = new Passenger();
        mockPassenger.setId(1L);
        Mockito.when(rideRepository.findRideByRideIdAndPassengerId(mockRide.getId(), mockPassenger.getId())).thenReturn(null);
        boolean expectedAnswer = false;
        boolean actualAnswer = rideService.checkIfPassengerExistInRide(mockRide.getId().toString(), mockPassenger.getId().toString());
        assertEquals(actualAnswer, expectedAnswer);
    }

    @Test
    public void shouldReturnTrueForExistingDriver(){
        Ride mockRide = new Ride();
        mockRide.setId(1L);
        Driver mockDriver = new Driver();
        mockDriver.setId(1L);
        Mockito.when(rideRepository.findRideByRideIdAndDriverId(mockRide.getId(), mockDriver.getId())).thenReturn(mockRide);
        boolean expectedAnswer = true;
        boolean actualAnswer = rideService.checkIfDriverExistInRide(mockRide.getId().toString(), mockDriver.getId().toString());
        assertEquals(actualAnswer, expectedAnswer);
    }

    @Test
    public void shouldReturnFalseForNonExistingDriver(){
        Ride mockRide = new Ride();
        mockRide.setId(1L);
        Driver mockDriver = new Driver();
        mockDriver.setId(1L);
        Mockito.when(rideRepository.findRideByRideIdAndPassengerId(mockRide.getId(), mockDriver.getId())).thenReturn(null);
        boolean expectedAnswer = false;
        boolean actualAnswer = rideService.checkIfDriverExistInRide(mockRide.getId().toString(), mockDriver.getId().toString());
        assertEquals(actualAnswer, expectedAnswer);
    }

    @Test
    public void shouldReturnRidesOfDriver(){
        Driver mockDriver = new Driver();
        mockDriver.setId(1L);
        Ride mockRide1 = new Ride();
        mockRide1.setDriver(mockDriver);
        Ride mockRide2 = new Ride();
        mockRide2.setDriver(mockDriver);
        Mockito.when(rideRepository.findAll()).thenReturn(Arrays.asList(mockRide1, mockRide2));
        assertEquals(rideService.getRidesOfDriver(mockDriver).size(), 2);
    }

    @Test
    public void shouldReturnActiveRideOfDriverIfExists(){
        Driver mockDriver = new Driver();
        mockDriver.setId(1L);
        Ride mockRide = new Ride();
        mockRide.setDriver(mockDriver);
        mockRide.setStatus(RideStatus.STARTED);
        Mockito.when(rideRepository.findAll()).thenReturn(Arrays.asList(mockRide));
        assertEquals(rideService.getaActiveRideByDriverId(mockDriver.getId().toString()), mockRide);
    }

    @Test
    public void shouldNotReturnActiveRideOfDriverIfDoesNotExist(){
        Driver mockDriver = new Driver();
        mockDriver.setId(1L);
        Ride mockRide = new Ride();
        mockRide.setDriver(mockDriver);
        mockRide.setStatus(RideStatus.PENDING);
        Mockito.when(rideRepository.findAll()).thenReturn(Arrays.asList(mockRide));
        assertNull(rideService.getaActiveRideByDriverId(mockDriver.getId().toString()));
    }

    @Test
    public void shouldReturnActiveRideOfPassengerIfExists(){
        Passenger mockPassenger = new Passenger();
        mockPassenger.setId(1L);
        Ride mockRide = new Ride();
        List<Passenger> mockList = new ArrayList<>();
        mockList.add(mockPassenger);
        mockRide.setPassengers(mockList);
        mockRide.setStatus(RideStatus.STARTED);
        Mockito.when(rideRepository.findAll()).thenReturn(Arrays.asList(mockRide));
        assertEquals(rideService.getActiveRideByPassengerId(mockPassenger.getId().toString()), mockRide);
    }

    @Test
    public void shouldNotReturnActiveRideOfPassengerIfDoesNotExist() {
        Passenger mockPassenger = new Passenger();
        mockPassenger.setId(1L);
        Ride mockRide = new Ride();
        List<Passenger> mockList = new ArrayList<>();
        mockList.add(mockPassenger);
        mockRide.setPassengers(mockList);
        mockRide.setStatus(RideStatus.PENDING);
        Mockito.when(rideRepository.findAll()).thenReturn(Arrays.asList(mockRide));
        assertNull(rideService.getActiveRideByPassengerId(mockPassenger.getId().toString()));
    }

    @Test
    public void shouldUpdateRideByStatus(){
        Ride mockRide = new Ride();
        mockRide.setId(1L);
        Mockito.when(rideRepository.findById(mockRide.getId())).thenReturn(Optional.of(mockRide));
        rideService.updateRideByStatus(mockRide.getId().toString(), RideStatus.ACCEPTED);
        assertEquals(mockRide.getStatus(), RideStatus.ACCEPTED);
    }

    @Test
    public void shouldSetRejectionLetter(){
        Ride mockRide = new Ride();
        mockRide.setId(1L);
        Mockito.when(rideRepository.findById(mockRide.getId())).thenReturn(Optional.of(mockRide));
        RejectionLetter mockLetter = new RejectionLetter();
        rideService.updateRideByRejectionLetter(mockRide.getId().toString(), mockLetter);
        assertEquals(mockRide.getLetter(), mockLetter);
    }

    @Test
    public void shouldReturnTrueDriverHasPendingRides(){
        Driver mockDriver = new Driver();
        mockDriver.setId(1L);
        Ride mockRide1 = new Ride();
        mockRide1.setDriver(mockDriver);
        mockRide1.setStatus(RideStatus.PENDING);
        Ride mockRide2 = new Ride();
        mockRide2.setDriver(mockDriver);
        mockRide2.setStatus(RideStatus.PENDING);
        Mockito.when(rideRepository.findAll()).thenReturn(Arrays.asList(mockRide1, mockRide2));
        boolean expectedAnswer = true;
        boolean actualAnswer = rideService.checkIfDriverHasPandingRides(mockDriver);
        assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void shouldNotReturnTrueDriverHasNotPendingRides(){
        Driver mockDriver = new Driver();
        mockDriver.setId(1L);
        Ride mockRide1 = new Ride();
        mockRide1.setStatus(RideStatus.PENDING);
        Ride mockRide2 = new Ride();
        mockRide2.setStatus(RideStatus.PENDING);
        Mockito.when(rideRepository.findAll()).thenReturn(Arrays.asList(mockRide1, mockRide2));
        boolean expectedAnswer = false;
        boolean actualAnswer = rideService.checkIfDriverHasPandingRides(mockDriver);
        assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void shouldParseToRide(){
        Driver mockDriver = new Driver();
        mockDriver.setId(1L);
        List<Ride> rides = new ArrayList<>();
        mockDriver.setRides(rides);
        List<RequestLocationDTO> locations = new ArrayList<>();
        RequestLocationDTO location = new RequestLocationDTO();
        RequestLocationWithAddressDTO departure = new RequestLocationWithAddressDTO("abc", 45.45, 20.20);
        location.setDeparture(departure);
        RequestLocationWithAddressDTO destination = new RequestLocationWithAddressDTO("def", 42.42, 23.23);
        location.setDestination(destination);
        locations.add(location);
        List<ResponsePassengerIdEmailDTO> passengers = new ArrayList<>();
        ResponsePassengerIdEmailDTO passenger = new ResponsePassengerIdEmailDTO(1L, "email@email.com");
        passengers.add(passenger);
        RequestRideDTO requestRide = new RequestRideDTO(locations, passengers, "VAN", true, false, new Date());
        Ride ride = rideService.parseToRide(requestRide, mockDriver);
        assertEquals(ride, mockDriver.getRides().get(0));
    }

    @Test
    public void shouldReturnTrueNotPendingAndNotStartedRide(){
        Ride mockRide = new Ride();
        mockRide.setId(1L);
        mockRide.setStatus(RideStatus.ACCEPTED);
        Mockito.when(rideRepository.findById(mockRide.getId())).thenReturn(Optional.of(mockRide));
        boolean expectedAnswer = true;
        boolean actualAnswer = rideService.checkIfNotPendingAndNotStartedById(mockRide.getId().toString());
        assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void shouldReturnFalsePendingAndNotStartedRide(){
        Ride mockRide = new Ride();
        mockRide.setId(1L);
        mockRide.setStatus(RideStatus.PENDING);
        Mockito.when(rideRepository.findById(mockRide.getId())).thenReturn(Optional.of(mockRide));
        boolean expectedAnswer = false;
        boolean actualAnswer = rideService.checkIfNotPendingAndNotStartedById(mockRide.getId().toString());
        assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void shouldReturnFalseNotPendingAndStartedRide(){
        Ride mockRide = new Ride();
        mockRide.setId(1L);
        mockRide.setStatus(RideStatus.STARTED);
        Mockito.when(rideRepository.findById(mockRide.getId())).thenReturn(Optional.of(mockRide));
        boolean expectedAnswer = false;
        boolean actualAnswer = rideService.checkIfNotPendingAndNotStartedById(mockRide.getId().toString());
        assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void shouldReturnTrueForNotAcceptedRide(){
        Ride mockRide = new Ride();
        mockRide.setId(1L);
        mockRide.setStatus(RideStatus.STARTED);
        Mockito.when(rideRepository.findById(mockRide.getId())).thenReturn(Optional.of(mockRide));
        boolean expectedAnswer = true;
        boolean actualAnswer = rideService.checkIfNotAcceptedById(mockRide.getId().toString());
        assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void shouldReturnFalseForAcceptedRide(){
        Ride mockRide = new Ride();
        mockRide.setId(1L);
        mockRide.setStatus(RideStatus.ACCEPTED);
        Mockito.when(rideRepository.findById(mockRide.getId())).thenReturn(Optional.of(mockRide));
        boolean expectedAnswer = false;
        boolean actualAnswer = rideService.checkIfNotAcceptedById(mockRide.getId().toString());
        assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void shouldReturnTrueForNotPendingRide(){
        Ride mockRide = new Ride();
        mockRide.setId(1L);
        mockRide.setStatus(RideStatus.STARTED);
        Mockito.when(rideRepository.findById(mockRide.getId())).thenReturn(Optional.of(mockRide));
        boolean expectedAnswer = true;
        boolean actualAnswer = rideService.checkIfNotPendingById(mockRide.getId().toString());
        assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void shouldReturnFalseForPendingRide(){
        Ride mockRide = new Ride();
        mockRide.setId(1L);
        mockRide.setStatus(RideStatus.PENDING);
        Mockito.when(rideRepository.findById(mockRide.getId())).thenReturn(Optional.of(mockRide));
        boolean expectedAnswer = false;
        boolean actualAnswer = rideService.checkIfNotPendingById(mockRide.getId().toString());
        assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void shouldReturnTrueForNotStartedRide(){
        Ride mockRide = new Ride();
        mockRide.setId(1L);
        mockRide.setStatus(RideStatus.PENDING);
        Mockito.when(rideRepository.findById(mockRide.getId())).thenReturn(Optional.of(mockRide));
        boolean expectedAnswer = true;
        boolean actualAnswer = rideService.checkIfNotStartedById(mockRide.getId().toString());
        assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void shouldReturnFalseForStartedRide(){
        Ride mockRide = new Ride();
        mockRide.setId(1L);
        mockRide.setStatus(RideStatus.STARTED);
        Mockito.when(rideRepository.findById(mockRide.getId())).thenReturn(Optional.of(mockRide));
        boolean expectedAnswer = false;
        boolean actualAnswer = rideService.checkIfNotStartedById(mockRide.getId().toString());
        assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void shouldReturnTrueForNotPendingAndNotAcceptedRide(){
        Ride mockRide = new Ride();
        mockRide.setId(1L);
        mockRide.setStatus(RideStatus.FINISHED);
        Mockito.when(rideRepository.findById(mockRide.getId())).thenReturn(Optional.of(mockRide));
        boolean expectedAnswer = true;
        boolean actualAnswer = rideService.checkIfNotPendingAndNotAcceptedById(mockRide.getId().toString());
        assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void shouldReturnFalseForPendingAndNotAcceptedRide(){
        Ride mockRide = new Ride();
        mockRide.setId(1L);
        mockRide.setStatus(RideStatus.PENDING);
        Mockito.when(rideRepository.findById(mockRide.getId())).thenReturn(Optional.of(mockRide));
        boolean expectedAnswer = false;
        boolean actualAnswer = rideService.checkIfNotPendingAndNotAcceptedById(mockRide.getId().toString());
        assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void shouldReturnFalseForNotPendingAndAcceptedRide(){
        Ride mockRide = new Ride();
        mockRide.setId(1L);
        mockRide.setStatus(RideStatus.ACCEPTED);
        Mockito.when(rideRepository.findById(mockRide.getId())).thenReturn(Optional.of(mockRide));
        boolean expectedAnswer = false;
        boolean actualAnswer = rideService.checkIfNotPendingAndNotAcceptedById(mockRide.getId().toString());
        assertEquals(expectedAnswer, actualAnswer);
    }

    @Test
    public void shouldUpdateByRejectLetterAndStatus(){
        Ride mockRide = new Ride();
        mockRide.setId(1L);
        Mockito.when(rideRepository.findById(mockRide.getId())).thenReturn(Optional.of(mockRide));
        RejectionLetter mockLetter = new RejectionLetter();
        mockRide.setLetter(mockLetter);
        mockRide.setStatus(RideStatus.ACCEPTED);
        Mockito.when(rideRepository.save(mockRide)).thenReturn(mockRide);
        rideService.updateRideByRejectionLetterAndStatus(mockRide.getId().toString(), mockLetter, RideStatus.ACCEPTED);
        assertEquals(mockRide.getLetter(), mockLetter);
        assertEquals(mockRide.getStatus(), RideStatus.ACCEPTED);
    }

    @Test
    public void shouldReturnNumberOfRidesForPassenger(){
        Ride mockRide = new Ride();
        mockRide.setId(1L);
        List<Passenger> mockList = new ArrayList<>();
        Passenger mockPassenger = new Passenger();
        mockPassenger.setId(1L);
        mockList.add(mockPassenger);
        mockRide.setPassengers(mockList);
        Mockito.when(rideRepository.findAll()).thenReturn(Arrays.asList(mockRide));
        assertEquals(rideService.getNumberOfRidesForPessanger(mockPassenger.getId().toString()), 1);
    }

    @Test
    public void shouldReturnPageableRidesForPassenger(){
        Long passengerId = 1L;
        Mockito.when(rideRepository.getRidesForPassenger(passengerId, PageRequest.of(0, 10))).thenReturn(new PageImpl<>(Arrays.asList(new Ride(), new Ride(), new Ride())));
        assertEquals(rideService.getRidesForPassenger(passengerId.toString(), PageRequest.of(0, 10)).getSize(), 3);
    }

    @Test
    public void shouldReturnPageableRidesForDriver(){
        Long driverId = 1L;
        Mockito.when(rideRepository.getRidesForDriver(driverId, PageRequest.of(0, 10))).thenReturn(new PageImpl<>(Arrays.asList(new Ride(), new Ride(), new Ride())));
        assertEquals(rideService.getRidesForDriver(driverId.toString(), PageRequest.of(0, 10)).getSize(), 3);
    }


}
