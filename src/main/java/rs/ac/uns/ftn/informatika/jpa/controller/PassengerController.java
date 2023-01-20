package rs.ac.uns.ftn.informatika.jpa.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.messages.MessageDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestPassengerDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePageDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseRideDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
import rs.ac.uns.ftn.informatika.jpa.model.UserActivation;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IPassengerService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IRideService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IUserActivationService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/passenger")
public class PassengerController{

    private IPassengerService passengerService;
    private IRideService rideService;
    private IUserActivationService userActivationService;

    @Autowired
    public PassengerController(IPassengerService passengerService, IRideService rideService, IUserActivationService userActivationService) {
        this.passengerService = passengerService;
        this.rideService = rideService;
        this.userActivationService = userActivationService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPassenger(@RequestBody RequestPassengerDTO requestPassengerDTO) throws Exception {

        if(this.passengerService.findByEmail(requestPassengerDTO.getEmail()) != null){
            return new ResponseEntity<>(new MessageDTO("User with that email already exists!"), HttpStatus.BAD_REQUEST);
        }

        Passenger passenger =  requestPassengerDTO.parseToPassenger();
        UserActivation activation = new UserActivation(passenger);
        passengerService.add(passenger);
        userActivationService.add(activation);
        return new ResponseEntity<>(passenger.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponsePageDTO> getPassengersPage(Pageable page) {

        int results = passengerService.getAll().size();
        List<ResponsePassengerDTO> responsePassengerDTOS = passengerService.getAsPageableResponse(page);
        return new ResponseEntity<>(new ResponsePageDTO(results, Arrays.asList(responsePassengerDTOS.toArray())), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPassenger(@PathVariable("id") String id) {

        if(!this.passengerService.getPassenger(id).isPresent()){
            return new ResponseEntity<>("Passenger does not exist!", HttpStatus.NOT_FOUND);
        }
        Passenger passenger = this.passengerService.getPassenger(id).get();
        return new ResponseEntity<>(passenger.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/activate/{activationId}")
    public ResponseEntity<?> activatePassenger(@PathVariable("activationId") String id) {

        if(!userActivationService.getUserActivation(id).isPresent()){
            return new ResponseEntity<>(new MessageDTO("Activation with entered id does not exis!"), HttpStatus.NOT_FOUND);
        }
        UserActivation activation = userActivationService.getUserActivation(id).get();
        if (activation.checkIfExpired()) {
            userActivationService.renewActivation(activation);
            return new ResponseEntity<>(new MessageDTO("Activation expired!"), HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<?> updatePassenger(@PathVariable("id") String id, @RequestBody RequestPassengerDTO requestPassengerDTO) {

        if(!this.passengerService.getPassenger(id).isPresent()){
            return new ResponseEntity<>("Passenger does not exist!", HttpStatus.NOT_FOUND);
        }
        Passenger passengerForUpdate = passengerService.getPassenger(id).get();
        Passenger passenger = requestPassengerDTO.parseToPassenger();
        passengerForUpdate.update(passenger);
        passengerService.add(passengerForUpdate);
        return new ResponseEntity<>(passengerForUpdate.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePageDTO> getPassengerRides(@PathVariable("id") String id, Pageable page) {

        List<ResponseRideDTO> responseRideDTOS = rideService.getPageableResponseRide(page, id);
        int passengerRidesNumber = rideService.getNumberOfRidesForPessanger(id);
        return new ResponseEntity<>(new ResponsePageDTO(passengerRidesNumber, Arrays.asList(responseRideDTOS.toArray())), HttpStatus.OK);
    }

}
