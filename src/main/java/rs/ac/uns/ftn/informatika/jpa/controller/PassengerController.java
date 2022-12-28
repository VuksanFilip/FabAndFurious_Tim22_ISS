package rs.ac.uns.ftn.informatika.jpa.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestPassengerDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerDTO;
import rs.ac.uns.ftn.informatika.jpa.dummy.PassengerDummy;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.PassengerService;

import java.util.Optional;

@RestController
@RequestMapping("/api/passenger")
public class PassengerController{

    private PassengerDummy passengerDummy = new PassengerDummy();

    private PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePassengerDTO> createPassenger(@RequestBody RequestPassengerDTO passenger) throws Exception {

        Long id = passengerDummy.passengerCounter.incrementAndGet();
        ResponsePassengerDTO passengerResponse = passenger.parseToResponse(id);

        Passenger passengerReal =  passenger.parseToPassenger(id);
        this.passengerService.add(passengerReal);

        passengerDummy.passengers.put(id, passenger.parseToPassenger(id));
        return new ResponseEntity<ResponsePassengerDTO>(passengerResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePassengerDTO> getPassenger(@PathVariable("id") String id) {

        Optional<Passenger> passenger = this.passengerService.getPassenger(id);

        return new ResponseEntity<ResponsePassengerDTO>(passenger.get().parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/activate/{activationId}")
    @ResponseStatus(HttpStatus.OK)
    public String activatePassenger(@PathVariable("activationId") Long id) {
        Passenger passenger = passengerDummy.passengers.get(id);
        return "Successful account activation";
    }

    @PutMapping (value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePassengerDTO> updatePassenger(@PathVariable("id") Long id, @RequestBody RequestPassengerDTO passengerDTO) {
        Passenger passengerForUpdate = passengerDummy.passengers.get(id);
        Passenger passenger = passengerDTO.parseToPassenger(id);
        passengerForUpdate.setFirstName(passenger.getFirstName());
        passengerForUpdate.setLastName(passenger.getLastName());
        passengerForUpdate.setPicture(passenger.getPicture());
        passengerForUpdate.setPhoneNumber(passenger.getPhoneNumber());
        passengerForUpdate.setEmail(passenger.getEmail());
        passengerForUpdate.setAddress(passenger.getAddress());
        passengerForUpdate.setPassword(passenger.getPassword());
        passengerDummy.passengers.put(id, passengerForUpdate);
        return new ResponseEntity<ResponsePassengerDTO>(passengerForUpdate.parseToResponse(), HttpStatus.OK);
    }
}
