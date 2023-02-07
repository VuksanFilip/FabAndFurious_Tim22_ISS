package rs.ac.uns.ftn.informatika.jpa.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import rs.ac.uns.ftn.informatika.jpa.repository.RideRepository;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class RideServiceTests {

    @Autowired
    RideServiceImpl rideService;

    @MockBean
    RideRepository rideRepository;


    private final Long driverId = 5L;
    private final Long wrongDriverId = 123L;
    private final Long passengerId =  2L;
    private final Long wrongPassengerId = 123L;
    private final Long rideId = 1L;
    private final Long wrongRideId = 123L;

    @Test
    @DisplayName("Should throw error when passenger tries to create ride but has one pending")
    public void shouldThrowErrorWhenCreatingRideIfPendingRideExists() {

//        public boolean checkIfPassengerExistInRide(String rideId, String passengerId){
//            if(this.rideRepository.findRideByRideIdAndPassengerId(Long.parseLong(rideId), Long.parseLong(passengerId)) != null){
//                return true;
//            }
//            return false;
//        }
//
//Mockito.
//
//        Ride mockRide = new Ride();
//        Mockito.when(this.rideRepository.findRideByRideIdAndPassengerId(rideId, RideStatus.PENDING)).thenReturn(mockRide));
//        assertThrows(ResponseStatusException.class, () -> rideService.save(mockRide,false,1));
    }



}
