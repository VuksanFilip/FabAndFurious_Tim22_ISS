package rs.ac.uns.ftn.informatika.jpa.controller;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.messages.MessageDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestPassengerDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestPassengerEmailDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestPassengerUpdateDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePageDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerIdEmailDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseRideNoStatusDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
import rs.ac.uns.ftn.informatika.jpa.model.UserActivation;
import rs.ac.uns.ftn.informatika.jpa.model.enums.Role;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IPassengerService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IRideService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IUserActivationService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IUserService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/passenger")
public class PassengerController{

    private final IPassengerService passengerService;
    private final IRideService rideService;
    private final IUserActivationService userActivationService;
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PassengerController(IPassengerService passengerService, IRideService rideService, IUserActivationService userActivationService, IUserService userService, PasswordEncoder passwordEncoder) {
        this.passengerService = passengerService;
        this.rideService = rideService;
        this.userActivationService = userActivationService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPassenger(@Valid @RequestBody RequestPassengerDTO requestPassengerDTO) throws Exception {

        if (this.userService.findByEmail(requestPassengerDTO.getEmail()) != null) {
            return new ResponseEntity<>(new MessageDTO("User with that email already exists!"), HttpStatus.BAD_REQUEST);
        }

        Passenger passenger = requestPassengerDTO.parseToPassenger();
        passenger.setPassword(passwordEncoder.encode(requestPassengerDTO.getPassword()));
        passenger.setRole(Role.PASSENGER);
        passenger.setActive(true);

        UserActivation activation = new UserActivation(passenger);

        passengerService.add(passenger);
        userActivationService.add(activation);
        return new ResponseEntity<>(passenger.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponsePageDTO> getAllPassengers(Pageable page) {

//        Long passengerId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
//        System.out.println(passengerId);

        int results = passengerService.getAll().size();
        List<ResponsePassengerDTO> responsePassengerDTOS = passengerService.getAsPageableResponse(page);

        return new ResponseEntity<>(new ResponsePageDTO(results, Arrays.asList(responsePassengerDTOS.toArray())), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'PASSENGER')")
    public ResponseEntity<?> getPassenger(@PathVariable("id") String id) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!this.passengerService.getPassenger(id).isPresent()){
            return new ResponseEntity<>(new MessageDTO("Passenger does not exist!"), HttpStatus.NOT_FOUND);
        }
        Passenger passenger = this.passengerService.getPassenger(id).get();
        return new ResponseEntity<>(passenger.parseToResponse(), HttpStatus.OK);
    }

    @PostMapping(value = "/exist", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPassengerIdByMail(@RequestBody RequestPassengerEmailDTO email){

        if(!this.passengerService.findByEmail(email.getEmail()).isPresent()){
            return new ResponseEntity<>(new MessageDTO("Passenger does not exist!"), HttpStatus.NOT_FOUND);
        }

        Passenger passenger = this.passengerService.findByEmail(email.getEmail()).get();
        return new ResponseEntity<>(new ResponsePassengerIdEmailDTO(passenger.getId(), passenger.getEmail()), HttpStatus.OK);
    }

    @GetMapping(value = "/activate/{activationId}")
    public ResponseEntity<?> activatePassenger(@PathVariable("activationId") String id) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!passengerService.getPassenger(id).isPresent()){
            return new ResponseEntity<>(new MessageDTO("Passenger with entered id does not exist!"), HttpStatus.NOT_FOUND);
        }
        UserActivation activation = userActivationService.getUserActivation(id).get();
        if (activation.checkIfExpired()) {
            userActivationService.renewActivation(activation);
            return new ResponseEntity<>(new MessageDTO("Activation expired, but also renewed!"), HttpStatus.BAD_REQUEST);
        }
        Passenger toActivate = (Passenger) activation.getUser();
        if (toActivate.isActive()) {
            return new ResponseEntity<>(new MessageDTO("Activation already activated!"), HttpStatus.BAD_REQUEST);
        }
        toActivate.setActive(true);
        passengerService.add(toActivate);
        return new ResponseEntity<>(new MessageDTO("Successful account activation!"), HttpStatus.OK);
    }

    @PutMapping (value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'PASSENGER')")
    public ResponseEntity<?> updatePassenger(@PathVariable("id") String id, @Valid @RequestBody RequestPassengerUpdateDTO requestPassengerDTO) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!this.passengerService.getPassenger(id).isPresent()){
            return new ResponseEntity<>("Passenger does not exist!", HttpStatus.NOT_FOUND);
        }
        Passenger passengerForUpdate = passengerService.getPassenger(id).get();
        passengerForUpdate.update(requestPassengerDTO);
        passengerService.add(passengerForUpdate);
        return new ResponseEntity<>(passengerForUpdate.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'PASSENGER')")
    public ResponseEntity<?> getPassengerRides(@PathVariable("id") String id, Pageable page) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        List<ResponseRideNoStatusDTO> responseRideDTOS = rideService.getPageableResponseRide(page, id);
        int passengerRidesNumber = rideService.getNumberOfRidesForPessanger(id);
        return new ResponseEntity<>(new ResponsePageDTO(passengerRidesNumber, Arrays.asList(responseRideDTOS.toArray())), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/rides", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'PASSENGER')")
    public ResponseEntity<?> getPassengerRides(@PathVariable("id") String id) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        List<ResponseRideNoStatusDTO> responseRideDTOS = rideService.getResponseRide(id);
        return new ResponseEntity<>(responseRideDTOS, HttpStatus.OK);
    }
}