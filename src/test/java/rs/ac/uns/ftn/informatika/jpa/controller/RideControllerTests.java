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
import rs.ac.uns.ftn.informatika.jpa.dto.request.*;
import rs.ac.uns.ftn.informatika.jpa.dto.response.*;
import rs.ac.uns.ftn.informatika.jpa.model.enums.RideStatus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RideControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private TestRestTemplate passengerRestTemplate, driverRestTemplate, adminRestTemplate;

    private String passengerJwtToken, driverJwtToken, adminJwtToken;

    private String passengerEmail = "marko.markovic@gmail.com", driverEmail = "andrea.katzenberger@gmail.com", adminEmail = adminJwtToken = "admin@gmail.com";

    private final String BASEAPI = "http://localhost:8084/api/ride";

    @BeforeAll
    public void setup() {
        createJwtTokens();
        createPassengerRestTemplate();
        createDriverRestTemplate();
        createAdminRestTemplate();
    }

    private void createJwtTokens() {
        createPassengerJwtToken();
        createDriverJwtToken();
        createAdminJwtToken();
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

    private void createAdminJwtToken() {
        HttpEntity<RequestLoginDTO> requestLogin = new HttpEntity<>(new RequestLoginDTO("admin@gmail.com", "fica123"));
        ResponseEntity<ResponseLoginDTO> responseLogin = this.restTemplate.exchange("/api/user/login",
                HttpMethod.POST,
                requestLogin,
                ResponseLoginDTO.class);
        this.adminJwtToken = responseLogin.getBody().getAccessToken();
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

    private void createAdminRestTemplate() {
        RestTemplateBuilder builder = new RestTemplateBuilder(rt -> rt.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + this.adminJwtToken);
            return execution.execute(request, body);
        }));
        this.adminRestTemplate = new TestRestTemplate(builder);
    }

    private List<RequestLocationDTO> createLocations() {
        List<RequestLocationDTO> requestLocationDTOS = new ArrayList<>();
        RequestLocationWithAddressDTO requestLocationWithAddressDTO1 = new RequestLocationWithAddressDTO("Bojanina kuca", 45.25523, 19.82325);
        RequestLocationWithAddressDTO requestLocationWithAddressDTO2 = new RequestLocationWithAddressDTO("Andreina kuca", 45.25643, 19.82980);
        RequestLocationDTO requestLocationDTO = new RequestLocationDTO(requestLocationWithAddressDTO1, requestLocationWithAddressDTO2);
        requestLocationDTOS.add(requestLocationDTO);

        return requestLocationDTOS;
    }

    private List<ResponsePassengerIdEmailDTO> createPassengers() {
        List<ResponsePassengerIdEmailDTO> responsePassengerIdEmailDTOS = new ArrayList<>();
        ResponsePassengerIdEmailDTO responsePassengerIdEmailDTO = new ResponsePassengerIdEmailDTO(2L, "marko.markovic@gmail.com");
        responsePassengerIdEmailDTOS.add(responsePassengerIdEmailDTO);
        return responsePassengerIdEmailDTOS;
    }

        private HttpEntity<RequestRideDTO> createRequestRideWithSomeData(){

        List<RequestLocationDTO> requestLocationDTOS = createLocations();
        List<ResponsePassengerIdEmailDTO> responsePassengerIdEmailDTOS = createPassengers();
        String vehicleType = "STANDARD";
        boolean babyTransport = true;
        boolean petTransport = true;
        Date scheduledTime = validDate();

        return new HttpEntity<>(new RequestRideDTO(
                requestLocationDTOS,
                responsePassengerIdEmailDTOS,
                vehicleType,
                babyTransport,
                petTransport,
                scheduledTime));
    }

    private HttpEntity<RequestRideDTO> createRequestRideWithInvalidData(){

        List<RequestLocationDTO> requestLocationDTOS = createLocations();
        List<ResponsePassengerIdEmailDTO> responsePassengerIdEmailDTOS = createPassengers();
        String vehicleType = "NO ENUM";
        boolean babyTransport = false;
        boolean petTransport = false;
        Date scheduledTime = validDate();

        return new HttpEntity<>(new RequestRideDTO(
                requestLocationDTOS,
                responsePassengerIdEmailDTOS,
                vehicleType,
                babyTransport,
                petTransport,
                scheduledTime));
    }

    private Date validDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 7);
        calendar.set(Calendar.HOUR, 7);
        return calendar.getTime();
    }

    private Date invalidDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 9999);
        return calendar.getTime();
    }

    private HttpEntity<RequestFavoriteRouteDTO> createFavoriteRoute(){

        String favoriteName = "Home - Work";
        List<RequestLocationDTO> locations = createLocations();
        List<ResponsePassengerIdEmailDTO> passengers = createPassengers();
        String vehicleType = "STANDARD";
        boolean babyTransport = true;
        boolean petTransport = true;

        return new HttpEntity<>(new RequestFavoriteRouteDTO(
                favoriteName,
                locations,
                passengers,
                vehicleType,
                babyTransport,
                petTransport));
    }

    private HttpEntity<RequestFavoriteRouteDTO> createFavoriteRouteInvalid(){

        String favoriteName = null;
        List<RequestLocationDTO> locations = createLocations();
        List<ResponsePassengerIdEmailDTO> passengers = createPassengers();
        String vehicleType = "NOT ENUM";
        boolean babyTransport = true;
        boolean petTransport = true;

        return new HttpEntity<>(new RequestFavoriteRouteDTO(
                favoriteName,
                locations,
                passengers,
                vehicleType,
                babyTransport,
                petTransport));
    }

    private HttpEntity<RequestRejectionLetterDTO> createRejectionLetter(){
        return new HttpEntity<>(new RequestRejectionLetterDTO("I broke my leg"));
    }

    private HttpEntity<RequestRejectionLetterDTO> createRejectionLetterInvalid(){
        return new HttpEntity<>(new RequestRejectionLetterDTO());
    }

    private HttpEntity<RequestPanicStringDTO> createReason(){
        return new HttpEntity<>(new RequestPanicStringDTO("He wants to kill me"));
    }

    private HttpEntity<RequestPanicStringDTO> createReasonInvalid(){
        return new HttpEntity<>(new RequestPanicStringDTO());
    }

    @Order(1)
    @Test
    @DisplayName("Cant create ride because of authorization")
    public void cantCreateRideIfUnauthorized(){

        HttpEntity<RequestRideDTO> requestRide = createRequestRideWithSomeData();

        ResponseEntity<String> response = this.restTemplate.exchange(
                "http://localhost:8084/api/ride",
                HttpMethod.GET,
                requestRide,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Order(2)
    @Test
    @DisplayName("Cant create ride because of roll")
    public void cantCreateRideIfForbiden(){

        HttpEntity<RequestRideDTO> requestRide = createRequestRideWithSomeData();

        ResponseEntity<String> response1 = this.driverRestTemplate.exchange(
                "http://localhost:8084/api/ride",
                HttpMethod.POST,
                requestRide,
                new ParameterizedTypeReference<String>() {
                }
        );
        ResponseEntity<String> response2 = this.adminRestTemplate.exchange(
                "http://localhost:8084/api/ride",
                HttpMethod.POST,
                requestRide,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.FORBIDDEN, response1.getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, response2.getStatusCode());
    }

    @Order(3)
    @Test
    @DisplayName("Creating ride for passenger")
    public void createRideForPassengerWithInvalidData(){

        HttpEntity<RequestRideDTO> requestRide = createRequestRideWithInvalidData();

        ResponseEntity<MessageDTO> response = this.passengerRestTemplate.exchange(
                "http://localhost:8084/api/ride",
                HttpMethod.POST,
                requestRide,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody().getMessage().replace("\n", ""), "Field vehicleType: Must be STANDARD/LUXURY/VAN");

    }

    @Order(4)
    @Test
    @DisplayName("Creating ride for passenger")
    public void createRideForPassenger(){

        HttpEntity<RequestRideDTO> requestRide = createRequestRideWithSomeData();

        ResponseEntity<ResponseRideNewDTO> response = this.passengerRestTemplate.exchange(
                "http://localhost:8084/api/ride",
                HttpMethod.POST,
                requestRide,
                new ParameterizedTypeReference<ResponseRideNewDTO>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getPassengers().size(), requestRide.getBody().getPassengers().size());
        assertEquals(response.getBody().getPassengers().get(0).getId(), requestRide.getBody().getPassengers().get(0).getId());
        assertEquals(response.getBody().getPassengers().get(0).getEmail(), requestRide.getBody().getPassengers().get(0).getEmail());
        assertEquals(response.getBody().getLocations().size(), requestRide.getBody().getLocations().size());
        assertEquals(response.getBody().getLocations(), requestRide.getBody().getLocations());
        assertEquals(response.getBody().getScheduledTime(), requestRide.getBody().getScheduledTime());
        assertTrue(response.getBody().getVehicleType().toString().equals(requestRide.getBody().getVehicleType()));
        assertEquals(response.getBody().getStatus(), RideStatus.PENDING);
    }

    @Order(5)
    @Test
    @DisplayName("Cant create ride for passenger baucause there is no free driver in that time ")
    public void createRideForPassengerButNoFreeDriverAtThatTime(){

        HttpEntity<RequestRideDTO> requestRide = createRequestRideWithSomeData();
        requestRide.getBody().setScheduledTime(invalidDate());

        ResponseEntity<MessageDTO> response = this.passengerRestTemplate.exchange(
                "http://localhost:8084/api/ride",
                HttpMethod.POST,
                requestRide,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "Recently there is no free drivers");
    }

    @Order(6)
    @Test
    @DisplayName("Cant create ride for passenger because there is no free driver in that time ")
    public void createRideForPassengerButNoFreeDriverWithThatVehicle(){

        HttpEntity<RequestRideDTO> requestRide = createRequestRideWithSomeData();
        requestRide.getBody().setVehicleType("LUXURY");


        ResponseEntity<MessageDTO> response = this.passengerRestTemplate.exchange(
                "http://localhost:8084/api/ride",
                HttpMethod.POST,
                requestRide,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "Recently there is no free drivers");
    }

    @Order(7)
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

    @Order(8)
    @Test
    @DisplayName("Cant find active driver ride because of roll")
    public void cantFindDriverActiveRide(){

        ResponseEntity<String> response = this.passengerRestTemplate.exchange(
                BASEAPI + "/driver/5/active",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Order(9)
    @Test
    @DisplayName("No active ride for valid driver")
    public void noActiveRideForValidDriver(){

        ResponseEntity<MessageDTO> response = this.driverRestTemplate.exchange(
                BASEAPI + "/driver/6/active",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "Active ride does not exist!");
    }

    @Order(10)
    @Test
    @DisplayName("No active ride for invalid driver")
    public void noActiveRideForInvalidDriver(){

        ResponseEntity<MessageDTO> response = this.driverRestTemplate.exchange(
                BASEAPI + "/driver/a/active",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "Id is not numeric");
    }

    @Order(11)
    @Test
    @DisplayName("Finding active ride for driver")
    public void findDriverActiveRide(){

        ResponseEntity<ResponseRideNewDTO> response = this.driverRestTemplate.exchange(
                BASEAPI + "/driver/5/active",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseRideNewDTO>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Order(12)
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

    @Order(13)
    @Test
    @DisplayName("Cant find active passenger ride because of roll")
    public void cantFindPassengerActiveRide(){

        ResponseEntity<String> response = this.driverRestTemplate.exchange(
                BASEAPI + "/passenger/5/active",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Order(14)
    @Test
    @DisplayName("No active ride for valid passenger")
    public void noActiveRideForValidPassenger(){

        ResponseEntity<MessageDTO> response = this.passengerRestTemplate.exchange(
                BASEAPI + "/passenger/3/active",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "Active ride does not exist!");

    }

    @Order(15)
    @Test
    @DisplayName("No active ride for invalid passenger")
    public void noActiveRideForInvalidPassenger(){

        ResponseEntity<MessageDTO> response = this.passengerRestTemplate.exchange(
                BASEAPI + "/passenger/a/active",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "Id is not numeric");

    }

    @Order(16)
    @Test
    @DisplayName("Finding active ride for passenger")
    public void findPassengerActiveRide(){

        ResponseEntity<MessageDTO> response = this.passengerRestTemplate.exchange(
                BASEAPI + "/passenger/2/active",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Order(17)
    @Test
    @DisplayName("Cant find ride because of authorization")
    public void cantFindRideByIdIfUnauthorized(){

        ResponseEntity<String> response = this.restTemplate.exchange(
                BASEAPI + "/1",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Order(18)
    @Test
    @DisplayName("Finding ride by invalid id")
    public void findRideByInvalidId(){

        ResponseEntity<MessageDTO> response = this.passengerRestTemplate.exchange(
                BASEAPI + "/a",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "Id is not numeric");
    }


    @Order(19)
    @Test
    @DisplayName("Finding ride by id")
    public void findRideById(){

        ResponseEntity<MessageDTO> response = this.passengerRestTemplate.exchange(
                BASEAPI + "/1",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Order(20)
    @Test
    @DisplayName("Cant withdraw ride because of authetication")
    public void cantWithdrawRideIfUnathorized(){

        ResponseEntity<String> response = this.restTemplate.exchange(
                BASEAPI + "/1/withdraw",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Order(21)
    @Test
    @DisplayName("Cant withdraw ride becouse of roll")
    public void cantWithdrawRideIfForbiden(){

        ResponseEntity<String> response1 = this.passengerRestTemplate.exchange(
                BASEAPI + "/1/withdraw",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );
        ResponseEntity<String> response2 = this.adminRestTemplate.exchange(
                BASEAPI + "/1/withdraw",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.FORBIDDEN, response1.getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, response2.getStatusCode());
    }

    @Order(22)
    @Test
    @DisplayName("Cant withdraw ride because of invalid id")
    public void withdrawRideWithInvalidId(){

        ResponseEntity<MessageDTO> response = this.driverRestTemplate.exchange(
                BASEAPI + "/a/withdraw",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "Id is not numeric");
    }

    @Order(23)
    @Test
    @DisplayName("Cant withdraw ride because status is not pending or started")
    public void cantWithdrawRideIfStatusIsNotPendingOrStarted(){

        ResponseEntity<MessageDTO> response = this.driverRestTemplate.exchange(
                BASEAPI + "/2/withdraw",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "Cannot withdraw a ride that is not in status PENDING or STARTED!");
    }

    @Order(24)
    @Test
    @DisplayName("Withdraw ride")
    public void withdrawRide(){

        ResponseEntity<ResponseRideNewDTO> response = this.driverRestTemplate.exchange(
                BASEAPI + "/1/withdraw",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<ResponseRideNewDTO>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getStatus(), RideStatus.REJECTED);
    }

    @Order(25)
    @Test
    @DisplayName("Cant accept ride because of authetication")
    public void cantAcceptRideIfUnathorized(){

        ResponseEntity<String> response = this.restTemplate.exchange(
                BASEAPI + "/5/accept",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Order(26)
    @Test
    @DisplayName("Cant accept ride because of roll")
    public void cantAcceptRideIfForbiden(){

        ResponseEntity<String> response1 = this.passengerRestTemplate.exchange(
                BASEAPI + "/5/accept",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );
        ResponseEntity<String> response2 = this.adminRestTemplate.exchange(
                BASEAPI + "/5/accept",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.FORBIDDEN, response1.getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, response2.getStatusCode());
    }

    @Order(27)
    @Test
    @DisplayName("Cant accept ride because of invalid id")
    public void acceptRideWithInvalidId(){

        ResponseEntity<MessageDTO> response = this.driverRestTemplate.exchange(
                BASEAPI + "/a/accept",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "Id is not numeric");
    }

    @Order(28)
    @Test
    @DisplayName("Cant accept ride because status is not pending")
    public void cantAcceptRideIfStatusIsNotPending(){

        ResponseEntity<MessageDTO> response = this.driverRestTemplate.exchange(
                BASEAPI + "/2/accept",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "Cannot start a ride that is not in status PENDING!");
    }

    @Test
    @Order(29)
    @DisplayName("Accept ride")
    public void acceptRide(){

        ResponseEntity<ResponseRideNewDTO> response = this.driverRestTemplate.exchange(
                BASEAPI + "/5/accept",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<ResponseRideNewDTO>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getStatus(), RideStatus.ACCEPTED);
    }

    @Order(30)
    @Test
    @DisplayName("Cant start ride because of authetication")
    public void cantStartRideIfUnathorized(){

        ResponseEntity<String> response = this.restTemplate.exchange(
                BASEAPI + "/5/accept",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Order(31)
    @Test
    @DisplayName("Cant start ride because of roll")
    public void cantStartRideIfForbiden(){

        ResponseEntity<String> reponse1 = this.passengerRestTemplate.exchange(
                BASEAPI + "/5/start",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );
        ResponseEntity<String> reponse2 = this.adminRestTemplate.exchange(
                BASEAPI + "/5/start",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.FORBIDDEN, reponse1.getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, reponse2.getStatusCode());
    }

    @Order(32)
    @Test
    @DisplayName("Cant start ride because of invalid id")
    public void startRideWithInvalidId(){

        ResponseEntity<MessageDTO> response = this.driverRestTemplate.exchange(
                BASEAPI + "/a/start",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "Id is not numeric");
    }

    @Order(33)
    @Test
    @DisplayName("Cant start ride because status is not accepted")
    public void cantStartRideIfStatusIsNotAccepted(){

        ResponseEntity<MessageDTO> response = this.driverRestTemplate.exchange(
                BASEAPI + "/2/start",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "Cannot start a ride that is not in status ACCEPTED!");
    }

    @Order(34)
    @Test
    @DisplayName("Start ride")
    public void startRide(){

        ResponseEntity<ResponseRideNewDTO> response = this.driverRestTemplate.exchange(
                BASEAPI + "/5/start",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<ResponseRideNewDTO>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getStatus(), RideStatus.STARTED);

    }

    @Order(35)
    @Test
    @DisplayName("Cant end ride because of authetication")
    public void cantEndRideIfUnathorized(){

        ResponseEntity<String> response = this.restTemplate.exchange(
                BASEAPI + "/5/end",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Order(36)
    @Test
    @DisplayName("Cant end ride becouse of roll")
    public void cantEndRideIfForbiden(){

        ResponseEntity<String> response1 = this.passengerRestTemplate.exchange(
                BASEAPI + "/5/end",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );
        ResponseEntity<String> response2 = this.adminRestTemplate.exchange(
                BASEAPI + "/5/end",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.FORBIDDEN, response1.getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, response2.getStatusCode());
    }

    @Order(37)
    @Test
    @DisplayName("Cant end ride because of invalid id")
    public void endRideWithInvalidId(){

        ResponseEntity<MessageDTO> response = this.driverRestTemplate.exchange(
                BASEAPI + "/a/end",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "Id is not numeric");
    }

    @Order(38)
    @Test
    @DisplayName("Cant end ride because status is not started")
    public void cantEndRideIfStatusIsNotStarted(){

        ResponseEntity<MessageDTO> response = this.driverRestTemplate.exchange(
                BASEAPI + "/2/end",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "Cannot endTime a ride that is not in status STARTED!");
    }


    @Test
    @Order(39)
    @DisplayName("End ride")
    public void endRide(){

        ResponseEntity<ResponseRideNewDTO> message = this.driverRestTemplate.exchange(
                BASEAPI + "/5/end",
                HttpMethod.PUT,
                null,
                new ParameterizedTypeReference<ResponseRideNewDTO>() {
                }
        );

        assertEquals(HttpStatus.OK, message.getStatusCode());
        assertEquals(message.getBody().getStatus(), RideStatus.FINISHED);

    }

    @Order(40)
    @Test
    @DisplayName("Cant cancel ride because of authetication")
    public void cantCancelRideIfUnathorized(){

        ResponseEntity<String> message = this.restTemplate.exchange(
                BASEAPI + "/3/cancel",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.UNAUTHORIZED, message.getStatusCode());
    }

    @Order(41)
    @Test
    @DisplayName("Cant cancel ride becouse of roll")
    public void cantCancelRideIfForbiden(){

        HttpEntity<RequestRejectionLetterDTO> requestRejectionLetter = createRejectionLetter();
        ResponseEntity<String> response1 = this.passengerRestTemplate.exchange(
                BASEAPI + "/3/cancel",
                HttpMethod.PUT,
                requestRejectionLetter,
                new ParameterizedTypeReference<String>() {
                }
        );
        ResponseEntity<String> response2 = this.adminRestTemplate.exchange(
                BASEAPI + "/3/cancel",
                HttpMethod.PUT,
                requestRejectionLetter,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.FORBIDDEN, response1.getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, response2.getStatusCode());
    }

    @Order(42)
    @Test
    @DisplayName("Cant cancel ride because of invalid id")
    public void cancelRideWithInvalidId(){

        HttpEntity<RequestRejectionLetterDTO> requestRejectionLetter = createRejectionLetter();
        ResponseEntity<MessageDTO> response = this.driverRestTemplate.exchange(
                BASEAPI + "/a/cancel",
                HttpMethod.PUT,
                requestRejectionLetter,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "Id is not numeric");
    }

    @Order(43)
    @Test
    @DisplayName("Cant cancel ride because status is not started")
    public void cantCancelRideIfStatusIsNotPendingOrAccepted(){

        HttpEntity<RequestRejectionLetterDTO> requestRejectionLetter = createRejectionLetter();
        ResponseEntity<MessageDTO> response = this.driverRestTemplate.exchange(
                BASEAPI + "/2/cancel",
                HttpMethod.PUT,
                requestRejectionLetter,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "Cannot cancel a ride that is not in status PENDING or ACCEPTED!");
    }

    @Test
    @Order(44)
    @DisplayName("Cancel ride")
    public void CancelRide(){

        HttpEntity<RequestRejectionLetterDTO> requestRejectionLetter = createRejectionLetter();
        ResponseEntity<ResponseRideNewDTO> response = this.driverRestTemplate.exchange(
                BASEAPI + "/3/cancel",
                HttpMethod.PUT,
                requestRejectionLetter,
                new ParameterizedTypeReference<ResponseRideNewDTO>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getStatus(), RideStatus.CANCELED);
        assertEquals(response.getBody().getRejection().getReason(), requestRejectionLetter.getBody().getReason());
    }

    @Order(45)
    @Test
    @DisplayName("Cant create panic because of authetication")
    public void cantPanicIfUnathorized(){

        ResponseEntity<String> response = this.restTemplate.exchange(
                BASEAPI + "/1/panic",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Order(46)
    @Test
    @DisplayName("Cant create panic becouse of roll")
    public void cantPanicIfForbiden(){

        HttpEntity<RequestPanicStringDTO> requestReason = createReason();
        ResponseEntity<String> response = this.adminRestTemplate.exchange(
                BASEAPI + "/1/panic",
                HttpMethod.PUT,
                requestReason,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @Order(47)
    @DisplayName("Cant create panic because of invalid ride id")
    public void panicForInvalidRide(){

        HttpEntity<RequestPanicStringDTO> requestReason = createReason();
        ResponseEntity<MessageDTO> response = this.passengerRestTemplate.exchange(
                BASEAPI + "/a/panic",
                HttpMethod.PUT,
                requestReason,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "Id is not numeric");

    }

    @Test
    @Order(48)
    @DisplayName("Cant create panic because of invalid ride id")
    public void panicForOtherRide(){

        HttpEntity<RequestPanicStringDTO> requestReason = createReason();
        ResponseEntity<MessageDTO> response = this.passengerRestTemplate.exchange(
                BASEAPI + "/3/panic",
                HttpMethod.PUT,
                requestReason,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "Cannot crate panic for this ride");
    }

    @Test
    @Order(49)
    @DisplayName("Create panic with invalid data")
    public void createPanicWithInvalidData(){

        HttpEntity<RequestPanicStringDTO> requestReason = createReasonInvalid();

        ResponseEntity<MessageDTO> response = this.passengerRestTemplate.exchange(
                BASEAPI + "/4/panic",
                HttpMethod.PUT,
                requestReason,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        System.out.println(response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody().getMessage().replace("\n", ""), "Field reason: Cant be empty");
    }

    @Test
    @Order(50)
    @DisplayName("Create panic by passenger")
    public void createPanicByPassenger(){

        HttpEntity<RequestPanicStringDTO> requestReason = createReason();

        ResponseEntity<ResponsePanicSmallerDataDTO> response = this.passengerRestTemplate.exchange(
                BASEAPI + "/4/panic",
                HttpMethod.PUT,
                requestReason,
                new ParameterizedTypeReference<ResponsePanicSmallerDataDTO>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getUser().getEmail(), this.passengerEmail);
        assertEquals(response.getBody().getRide().getId(), 4L);
        assertEquals(response.getBody().getReason(), requestReason.getBody().getReason());
    }

    @Test
    @Order(51)
    @DisplayName("Create panic by driver")
    public void createPanicByDriver(){

        HttpEntity<RequestPanicStringDTO> requestReason = createReason();

        ResponseEntity<ResponsePanicSmallerDataDTO> response = this.driverRestTemplate.exchange(
                BASEAPI + "/4/panic",
                HttpMethod.PUT,
                requestReason,
                new ParameterizedTypeReference<ResponsePanicSmallerDataDTO>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getUser().getEmail(), this.driverEmail);
        assertEquals(response.getBody().getRide().getId(), 4L);
        assertEquals(response.getBody().getReason(), requestReason.getBody().getReason());
    }

    @Order(52)
    @Test
    @DisplayName("Cant create favorite routes because of authorization")
    public void createFavoriteRoutesIfUnathorized(){

        ResponseEntity<String> response = this.restTemplate.exchange(
                BASEAPI + "/favorites",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Order(53)
    @Test
    @DisplayName("Cant create favorite routes becouse of roll")
    public void createFavoritRoutesIfForbiden(){

        HttpEntity<RequestFavoriteRouteDTO> requestReason = createFavoriteRoute();
        ResponseEntity<String> response1 = this.adminRestTemplate.exchange(
                BASEAPI + "/favorites",
                HttpMethod.POST,
                requestReason,
                new ParameterizedTypeReference<String>() {
                }
        );
        ResponseEntity<String> response2 = this.driverRestTemplate.exchange(
                BASEAPI + "/favorites",
                HttpMethod.POST,
                requestReason,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.FORBIDDEN, response1.getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, response2.getStatusCode());

    }

    @Order(54)
    @Test
    @DisplayName("Cant create favorit routes becouse of invalid data")
    public void createFavoritRoutesIfInvalidData(){

        HttpEntity<RequestFavoriteRouteDTO> requestReason = createFavoriteRouteInvalid();
        ResponseEntity<MessageDTO> response = this.passengerRestTemplate.exchange(
                BASEAPI + "/favorites",
                HttpMethod.POST,
                requestReason,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Order(55)
    @Test
    @DisplayName("Create favorite routes")
    public void createFavoritRoutes(){

        HttpEntity<RequestFavoriteRouteDTO> requestReason = createFavoriteRoute();
        ResponseEntity<ResponseFavoriteRouteWithScheduledTimeDTO> response = this.passengerRestTemplate.exchange(
                BASEAPI + "/favorites",
                HttpMethod.POST,
                requestReason,
                new ParameterizedTypeReference<ResponseFavoriteRouteWithScheduledTimeDTO>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getPassengers().size(), requestReason.getBody().getPassengers().size());
        assertEquals(response.getBody().getPassengers().get(0).getEmail(), requestReason.getBody().getPassengers().get(0).getEmail());
        assertEquals(response.getBody().getPassengers().get(0).getId(), requestReason.getBody().getPassengers().get(0).getId());
        assertEquals(response.getBody().getLocations().size(), requestReason.getBody().getLocations().size());
        assertEquals(response.getBody().getFavoriteName(), requestReason.getBody().getFavoriteName());
        assertEquals(response.getBody().isBabyTransport(), requestReason.getBody().isBabyTransport());
        assertEquals(response.getBody().isPetTransport(), requestReason.getBody().isPetTransport());
        assertEquals(response.getBody().getVehicleType().toString(), requestReason.getBody().getVehicleType());
    }

    @Order(56)
    @Test
    @DisplayName("Create favorite routes that exeed 10")
    public void createFavoritRoutesWithMoreThan10(){

        HttpEntity<RequestFavoriteRouteDTO> requestReason = createFavoriteRoute();
        ResponseEntity<MessageDTO> response = this.passengerRestTemplate.exchange(
                BASEAPI + "/favorites",
                HttpMethod.POST,
                requestReason,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "Number of favorite rides cannot exceed 10!");
    }

    @Order(57)
    @Test
    @DisplayName("Cant find favorite routes because of authorization")
    public void findFavoriteRoutesIfUnauthorized(){

        ResponseEntity<String> message = this.restTemplate.exchange(
                BASEAPI + "/favorites",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.UNAUTHORIZED, message.getStatusCode());
    }

    @Order(58)
    @Test
    @DisplayName("Cant find favorite routes because of wrong roll")
    public void findFavoriteRoutesIfForbiden(){

        ResponseEntity<MessageDTO> response1 = this.driverRestTemplate.exchange(
                BASEAPI + "/favorites",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );
        ResponseEntity<MessageDTO> response2 = this.adminRestTemplate.exchange(
                BASEAPI + "/favorites",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.FORBIDDEN, response1.getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, response2.getStatusCode());
    }

    @Order(59)
    @Test
    @DisplayName("Finding favorite routes")
    public void findFavoriteRoutes(){

        ResponseEntity<List<ResponseFavoriteRouteDTO>> acceptResponse = this.passengerRestTemplate.exchange(
                BASEAPI + "/favorites",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ResponseFavoriteRouteDTO>>() {
                }
        );
        assertEquals(HttpStatus.OK, acceptResponse.getStatusCode());
    }

    @Order(60)
    @Test
    @DisplayName("Cant delete favorite routes because of authorization")
    public void deleteFavoriteRoutesIfUnathorized(){

        ResponseEntity<String> message = this.restTemplate.exchange(
                BASEAPI + "/favorites/1",
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.UNAUTHORIZED, message.getStatusCode());
    }

    @Order(61)
    @Test
    @DisplayName("Cant delete favorite routes becouse of roll")
    public void deleteFavoriteRoutesIfForbiden(){

        HttpEntity<RequestFavoriteRouteDTO> requestReason = createFavoriteRoute();
        ResponseEntity<String> response1 = this.adminRestTemplate.exchange(
                BASEAPI + "/favorites/1",
                HttpMethod.DELETE,
                requestReason,
                new ParameterizedTypeReference<String>() {
                }
        );
        ResponseEntity<String> response2 = this.driverRestTemplate.exchange(
                BASEAPI + "/favorites/1",
                HttpMethod.DELETE,
                requestReason,
                new ParameterizedTypeReference<String>() {
                }
        );

        assertEquals(HttpStatus.FORBIDDEN, response1.getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, response2.getStatusCode());
    }

    @Order(62)
    @Test
    @DisplayName("Delete favorite")
    public void deleteFavoriteRoutes(){

        HttpEntity<RequestFavoriteRouteDTO> requestReason = createFavoriteRoute();
        ResponseEntity<MessageDTO> response = this.passengerRestTemplate.exchange(
                BASEAPI + "/favorites/1",
                HttpMethod.DELETE,
                requestReason,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Order(63)
    @Test
    @DisplayName("Cant delete favorite routes because of invalid id")
    public void deleteFavoriteRoutesIfInvalid(){

        HttpEntity<RequestFavoriteRouteDTO> requestReason = createFavoriteRoute();
        ResponseEntity<MessageDTO> response = this.passengerRestTemplate.exchange(
                BASEAPI + "/favorites/1",
                HttpMethod.DELETE,
                requestReason,
                new ParameterizedTypeReference<MessageDTO>() {
                }
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody().getMessage(), "Favorite location does not exist!");
    }
}