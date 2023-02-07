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

    @Order(1)
    @Test
    @DisplayName("Cant create ride because of authorization")
    public void cantCreateRideIfUnauthorized(){

        HttpEntity<RequestRideDTO> requestRide = createRequestRideWithSomeData();

        ResponseEntity<String> message = this.restTemplate.exchange(
                "http://localhost:8084/api/ride",
                HttpMethod.POST,
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

        ResponseEntity<MessageDTO> message = this.passengerRestTemplate.exchange(
                "http://localhost:8084/api/ride",
                HttpMethod.POST,
                requestRide,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.OK, message.getStatusCode());
    }

    @Order(4)
    @Test
    @DisplayName("Cant create ride for passenger baucause there is no free driver ")
    public void createRideForPassengerButNoFreeDriver(){

        HttpEntity<RequestRideDTO> requestRide = createRequestRideWithSomeData();

        ResponseEntity<MessageDTO> message = this.passengerRestTemplate.exchange(
                "http://localhost:8084/api/ride",
                HttpMethod.POST,
                requestRide,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        System.out.println(message.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, message.getStatusCode());
    }

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
//    @Order(1)
//    @Test
//    @DisplayName("Finding active ride for driver")
//    public void findDriverActiveRide(){
//
//        ResponseEntity<MessageDTO> acceptResponse = this.driverRestTemplate.exchange(
//                BASEAPI + "/driver/5/active",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<MessageDTO>() {
//                }
//        );
//
//        assertEquals(HttpStatus.OK, acceptResponse.getStatusCode());
//    }
//
//    @Order(2)
//    @Test
//    @DisplayName("Not finding active ride for valid driver")
//    public void dontFindDriverActiveRide(){
//
//        ResponseEntity<MessageDTO> acceptResponse = this.driverRestTemplate.exchange(
//                BASEAPI + "/driver/6/active",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<MessageDTO>() {
//                }
//        );
//
//        assertEquals(HttpStatus.NOT_FOUND, acceptResponse.getStatusCode());
//    }
//
//    @Order(3)
//    @Test
//    @DisplayName("Not finding active ride for invalid driver id")
//    public void dontDriverRideWithInvalidId(){
//
//        ResponseEntity<MessageDTO> acceptResponse = this.driverRestTemplate.exchange(
//                BASEAPI + "/driver/a/active",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<MessageDTO>() {
//                }
//        );
//
//        assertEquals(HttpStatus.NOT_FOUND, acceptResponse.getStatusCode());
//    }
//
//    @Order(4)
//    @Test
//    @DisplayName("Finding active ride for passenger")
//    public void findPassengerActiveRide(){
//
//        ResponseEntity<MessageDTO> acceptResponse = this.passengerRestTemplate.exchange(
//                BASEAPI + "/passenger/2/active",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<MessageDTO>() {
//                }
//        );
//
//        assertEquals(HttpStatus.OK, acceptResponse.getStatusCode());
//    }
//
//    @Order(5)
//    @Test
//    @DisplayName("Not finding active ride for valid passenger")
//    public void dontFindPassengerActiveRide(){
//
//        ResponseEntity<MessageDTO> acceptResponse = this.passengerRestTemplate.exchange(
//                BASEAPI + "/passenger/4/active",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<MessageDTO>() {
//                }
//        );
//
//        assertEquals(HttpStatus.NOT_FOUND, acceptResponse.getStatusCode());
//    }
//
//    @Order(6)
//    @Test
//    @DisplayName("Not finding active ride for invalid driver id")
//    public void dontFindPassengerRideWithInvalidId(){
//
//        ResponseEntity<MessageDTO> acceptResponse = this.passengerRestTemplate.exchange(
//                BASEAPI + "/passenger/a/active",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<MessageDTO>() {
//                }
//        );
//
//        assertEquals(HttpStatus.NOT_FOUND, acceptResponse.getStatusCode());
//    }
//
////    @Order(7)
////    @Test
////    @DisplayName("Not finding active ride for invalid driver id")
////    public void dontFindPassengerRideWithInvalidId(){
////
////        ResponseEntity<MessageDTO> acceptResponse = this.passengerRestTemplate.exchange(
////                BASEAPI + "/passenger/a/active",
////                HttpMethod.GET,
////                null,
////                new ParameterizedTypeReference<MessageDTO>() {
////                }
////        );
////
////        assertEquals(HttpStatus.NOT_FOUND, acceptResponse.getStatusCode());
////    }
//
//
////    @Test
////    @DisplayName("Tries to accept non existing ride")
////    public void getActivaRideForDriver(){
////
////        ResponseEntity<MessageDTO> acceptResponse = this.driverRestTemplate.exchange(
////                BASEAPI + "/1/accept",
////                HttpMethod.PUT,
////                null,
////                new ParameterizedTypeReference<MessageDTO>() {
////                }
////        );
////
////        assertEquals(HttpStatus.OK, acceptResponse.getStatusCode());
////    }
}