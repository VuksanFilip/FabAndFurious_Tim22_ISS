package rs.ac.uns.ftn.informatika.jpa.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import rs.ac.uns.ftn.informatika.jpa.dto.messages.MessageDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLocationWithAddressDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLoginDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestRideDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseLoginDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerIdEmailDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseRideDTO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RideControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private TestRestTemplate passengerRestTemplate, driverRestTemplate;

    private String passengerJwtToken, driverJwtToken;
    private final String BASEAPI = "http://localhost:8084/api/ride";

    @BeforeAll
    public void setup() {
        createJwtTokens();
        createPassengerRestTemplate();
        createDriverRestTemplate();
    }

    private void createJwtTokens() {
        createPassengerJwtToken();
        createDriverJwtToken();
    }

    private void createPassengerJwtToken() {
        HttpEntity<RequestLoginDTO> requestLogin = new HttpEntity<>(new RequestLoginDTO("marko.markovic@gmail.com", "marko123"));
        ResponseEntity<ResponseLoginDTO> responseLogin = this.restTemplate.exchange("/api/user/login",
                HttpMethod.POST,
                requestLogin,
                ResponseLoginDTO.class);
        this.passengerJwtToken = responseLogin.getBody().getAccessToken();
    }

    private void createDriverJwtToken() {
        HttpEntity<RequestLoginDTO> requestLogin = new HttpEntity<>(new RequestLoginDTO("andrea.katzenberger@gmail.com", "andrea123"));
        ResponseEntity<ResponseLoginDTO> responseLogin = this.restTemplate.exchange("/api/user/login",
                HttpMethod.POST,
                requestLogin,
                ResponseLoginDTO.class);
        this.driverJwtToken = responseLogin.getBody().getAccessToken();
    }

    private void createPassengerRestTemplate() {
        RestTemplateBuilder builder = new RestTemplateBuilder(rt -> rt.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + this.passengerJwtToken);
            return execution.execute(request, body);
        }));
        this.passengerRestTemplate = new TestRestTemplate(builder);
    }

    private void createDriverRestTemplate() {
        RestTemplateBuilder builder = new RestTemplateBuilder(rt -> rt.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + this.driverJwtToken);
            return execution.execute(request, body);
        }));
        this.driverRestTemplate = new TestRestTemplate(builder);
    }

    private HttpEntity<RequestRideDTO> createRequestRideWithSomeData(){

        List<RequestLocationDTO> requestLocationDTOS = new ArrayList<>();
        RequestLocationWithAddressDTO requestLocationWithAddressDTO1 = new RequestLocationWithAddressDTO("Bojanina kuca", 45.25523, 19.82325);
        RequestLocationWithAddressDTO requestLocationWithAddressDTO2 = new RequestLocationWithAddressDTO("Andreina kuca", 45.25643, 19.82980);
        RequestLocationDTO requestLocationDTO = new RequestLocationDTO(requestLocationWithAddressDTO1, requestLocationWithAddressDTO2);
        requestLocationDTOS.add(requestLocationDTO);

        List<ResponsePassengerIdEmailDTO> responsePassengerIdEmailDTOS = new ArrayList<>();
        ResponsePassengerIdEmailDTO responsePassengerIdEmailDTO = new ResponsePassengerIdEmailDTO(2L, "marko.markovic@gmail.com");
        responsePassengerIdEmailDTOS.add(responsePassengerIdEmailDTO);

        String vehicleType = "STANDARD";
        boolean babyTransport = true;
        boolean petTransport = true;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 7);
        calendar.set(Calendar.HOUR, 7);

        Date scheduledTime = calendar.getTime();

        return new HttpEntity<>(new RequestRideDTO(
                requestLocationDTOS,
                responsePassengerIdEmailDTOS,
                vehicleType,
                babyTransport,
                petTransport,
                scheduledTime));
    }

    private Date invalidDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 9999);
        return calendar.getTime();
    }

    @Order(1)
    @Test
    @DisplayName("Cant create ride because of authorization")
    public void cantCreateRideIfUnauthorized(){

        HttpEntity<RequestRideDTO> requestRide = createRequestRideWithSomeData();

        ResponseEntity<String> message = this.restTemplate.exchange(
                "http://localhost:8084/api/ride",
                HttpMethod.GET,
                requestRide,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.UNAUTHORIZED, message.getStatusCode());
    }

    @Order(2)
    @Test
    @DisplayName("Cant create ride because of roll")
    public void cantCreateRideIfForbiden(){

        HttpEntity<RequestRideDTO> requestRide = createRequestRideWithSomeData();

        ResponseEntity<String> message = this.driverRestTemplate.exchange(
                "http://localhost:8084/api/ride",
                HttpMethod.POST,
                requestRide,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.FORBIDDEN, message.getStatusCode());
    }

    @Order(3)
    @Test
    @DisplayName("Creating ride for passenger")
    public void createRideForPassenger(){

        HttpEntity<RequestRideDTO> requestRide = createRequestRideWithSomeData();

        ResponseEntity<ResponseRideDTO> message = this.passengerRestTemplate.exchange(
                "http://localhost:8084/api/ride",
                HttpMethod.POST,
                requestRide,
                new ParameterizedTypeReference<ResponseRideDTO>() {
                }
        );

        System.out.println(message.getBody().getStatus());

        assertEquals(HttpStatus.OK, message.getStatusCode());
    }

    @Order(4)
    @Test
    @DisplayName("Cant create ride for passenger baucause there is no free driver in that time ")
    public void createRideForPassengerButNoFreeDriverAtThatTime(){

        HttpEntity<RequestRideDTO> requestRide = createRequestRideWithSomeData();
        requestRide.getBody().setScheduledTime(invalidDate());

        ResponseEntity<MessageDTO> message = this.passengerRestTemplate.exchange(
                "http://localhost:8084/api/ride",
                HttpMethod.POST,
                requestRide,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, message.getStatusCode());
    }

    @Order(5)
    @Test
    @DisplayName("Cant create ride for passenger because there is no free driver in that time ")
    public void createRideForPassengerButNoFreeDriverWithThatVehicle(){

        HttpEntity<RequestRideDTO> requestRide = createRequestRideWithSomeData();
        requestRide.getBody().setVehicleType("LUXURY");


        ResponseEntity<MessageDTO> message = this.passengerRestTemplate.exchange(
                "http://localhost:8084/api/ride",
                HttpMethod.POST,
                requestRide,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, message.getStatusCode());
    }

    @Order(6)
    @Test
    @DisplayName("Cant find driver active ride because of authorization")
    public void cantFindDriverActiveRideIfUnathorized(){

        ResponseEntity<String> message = this.restTemplate.exchange(
                BASEAPI + "/driver/5/active",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.UNAUTHORIZED, message.getStatusCode());
    }

    @Order(7)
    @Test
    @DisplayName("Cant find active driver ride because of roll")
    public void cantFindDriverActiveRide(){

        ResponseEntity<String> message = this.passengerRestTemplate.exchange(
                BASEAPI + "/driver/5/active",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.FORBIDDEN, message.getStatusCode());
    }

    @Order(8)
    @Test
    @DisplayName("No active ride for valid driver")
    public void noActiveRideForValidDriver(){

        ResponseEntity<MessageDTO> acceptResponse = this.driverRestTemplate.exchange(
                BASEAPI + "/driver/6/active",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, acceptResponse.getStatusCode());
    }

    @Order(9)
    @Test
    @DisplayName("No active ride for invalid driver")
    public void noActiveRideForInvalidDriver(){

        ResponseEntity<MessageDTO> acceptResponse = this.driverRestTemplate.exchange(
                BASEAPI + "/driver/a/active",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, acceptResponse.getStatusCode());
    }

    @Order(10)
    @Test
    @DisplayName("Finding active ride for driver")
    public void findDriverActiveRide(){

        ResponseEntity<MessageDTO> acceptResponse = this.driverRestTemplate.exchange(
                BASEAPI + "/driver/5/active",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.OK, acceptResponse.getStatusCode());
    }

    @Order(11)
    @Test
    @DisplayName("Cant find passenger active ride because of authorization")
    public void cantFindPassengerActiveRideIfUnathorized(){

        ResponseEntity<String> message = this.restTemplate.exchange(
                BASEAPI + "/passenger/2/active",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.UNAUTHORIZED, message.getStatusCode());
    }

    @Order(12)
    @Test
    @DisplayName("Cant find active passenger ride because of roll")
    public void cantFindPassengerActiveRide(){

        ResponseEntity<String> message = this.driverRestTemplate.exchange(
                BASEAPI + "/passenger/5/active",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.FORBIDDEN, message.getStatusCode());
    }

    @Order(13)
    @Test
    @DisplayName("No active ride for valid passenger")
    public void noActiveRideForValidPassenger(){

        ResponseEntity<MessageDTO> acceptResponse = this.passengerRestTemplate.exchange(
                BASEAPI + "/passenger/3/active",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, acceptResponse.getStatusCode());
    }

    @Order(14)
    @Test
    @DisplayName("No active ride for invalid passenger")
    public void noActiveRideForInvalidPassenger(){

        ResponseEntity<MessageDTO> acceptResponse = this.passengerRestTemplate.exchange(
                BASEAPI + "/passenger/a/active",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, acceptResponse.getStatusCode());
    }

    @Order(15)
    @Test
    @DisplayName("Finding active ride for passenger")
    public void findPassengerActiveRide(){

        ResponseEntity<MessageDTO> acceptResponse = this.passengerRestTemplate.exchange(
                BASEAPI + "/passenger/2/active",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.OK, acceptResponse.getStatusCode());
    }

    @Order(16)
    @Test
    @DisplayName("Cant find ride because of authorization")
    public void cantFindRideByIdIfUnauthorized(){

        ResponseEntity<String> message = this.restTemplate.exchange(
                BASEAPI + "/1",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.UNAUTHORIZED, message.getStatusCode());
    }

    @Order(17)
    @Test
    @DisplayName("Finding ride by invalid id")
    public void findRideByInvalidId(){

        ResponseEntity<MessageDTO> acceptResponse = this.passengerRestTemplate.exchange(
                BASEAPI + "/a",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, acceptResponse.getStatusCode());
    }


    @Order(18)
    @Test
    @DisplayName("Finding ride by id")
    public void findRideById(){

        ResponseEntity<MessageDTO> acceptResponse = this.passengerRestTemplate.exchange(
                BASEAPI + "/1",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.OK, acceptResponse.getStatusCode());
    }

    @Order(19)
    @Test
    @DisplayName("Cant withdraw ride because of authetication")
    public void cantWithdrawRideIfUnathorized(){

        ResponseEntity<String> message = this.restTemplate.exchange(
                BASEAPI + "/1/withdraw",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.UNAUTHORIZED, message.getStatusCode());
    }

    @Order(20)
    @Test
    @DisplayName("Cant withdraw ride becouse of roll")
    public void cantWithdrawRideIfForbiden(){

        ResponseEntity<String> message = this.passengerRestTemplate.exchange(
                BASEAPI + "/1/withdraw",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.FORBIDDEN, message.getStatusCode());
    }

    @Order(21)
    @Test
    @DisplayName("Cant withdraw ride because of roll")
    public void withdrawRideWithInvalidId(){

        ResponseEntity<String> message = this.driverRestTemplate.exchange(
                BASEAPI + "/a/withdraw",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, message.getStatusCode());
    }

    @Order(22)
    @Test
    @DisplayName("Cant withdraw ride because status is not pending or started")
    public void cantWithdrawRideIfStatusIsNotPendingOrStarted(){

        ResponseEntity<String> message = this.driverRestTemplate.exchange(
                BASEAPI + "/2/withdraw",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.BAD_REQUEST, message.getStatusCode());
    }

    @Order(23)
    @Test
    @DisplayName("Withdraw ride")
    public void withdrawRide(){

        ResponseEntity<MessageDTO> message = this.driverRestTemplate.exchange(
                BASEAPI + "/1/withdraw",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.OK, message.getStatusCode());
    }

    @Order(24)
    @Test
    @DisplayName("Cant accept ride because of authetication")
    public void cantAcceptRideIfUnathorized(){

        ResponseEntity<String> message = this.restTemplate.exchange(
                BASEAPI + "/5/accept",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.UNAUTHORIZED, message.getStatusCode());
    }

    @Order(25)
    @Test
    @DisplayName("Cant accept ride becouse of roll")
    public void cantAcceptRideIfForbiden(){

        ResponseEntity<String> message = this.passengerRestTemplate.exchange(
                BASEAPI + "/5/accept",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.FORBIDDEN, message.getStatusCode());
    }

    @Order(26)
    @Test
    @DisplayName("Cant accept ride because status is not pending")
    public void cantAcceptRideIfStatusIsNotPending(){

        ResponseEntity<String> message = this.driverRestTemplate.exchange(
                BASEAPI + "/2/accept",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.BAD_REQUEST, message.getStatusCode());
    }

//    @Order(27)
//    @Test
//    @DisplayName("Accept ride")
//    public void acceptRide(){
//
//        ResponseEntity<MessageDTO> message = this.driverRestTemplate.exchange(
//                BASEAPI + "/5/accept",
//                HttpMethod.PUT,
//                null,
//                new ParameterizedTypeReference<MessageDTO>() {
//                }
//        );
//
//        ResponseEntity<ResponseRideDTO> responseRideDTO = this.passengerRestTemplate.getForEntity("http://localhost:8084/api/ride", ResponseRideDTO.class, 1);
//
//        System.out.println(responseRideDTO.getBody().getId());
//
//        System.out.println(message.getBody().getMessage());
//        assertEquals(HttpStatus.OK, message.getStatusCode());
//    }
    //    @Test
//    @DisplayName("Tries to accept non existing ride")
//    public void invalid_passenger(){
//
//
//        ResponseEntity<MessageDTO> acceptResponse = this.driverRestTemplate.exchange(
//                BASEAPI + "/1234/accept",
//                HttpMethod.PUT,
//                null,
//                new ParameterizedTypeReference<MessageDTO>() {
//                }
//        );
//
//        assertEquals("Ride does not exist!", acceptResponse.getBody().getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, acceptResponse.getStatusCode());
//
//    }
//
}