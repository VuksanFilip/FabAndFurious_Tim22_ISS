package rs.ac.uns.ftn.informatika.jpa.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.create.CreatePassengerDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.PassengerResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dummy.PassengerDummy;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;

@RestController
@RequestMapping("/api/passenger")
public class PassengerController{
    private PassengerDummy passengerDummy = new PassengerDummy();

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassengerResponseDTO> createPassenger(@RequestBody CreatePassengerDTO passenger) throws Exception {
        Long id = passengerDummy.passengerCounter.incrementAndGet();
        PassengerResponseDTO passengerResponse = passenger.parseToResponse(id);
        passengerDummy.passengers.put(id, passenger.parseToPassenger(id));
        return new ResponseEntity<PassengerResponseDTO>(passengerResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassengerResponseDTO> getPassenger(@PathVariable("id") Long id) {
        Passenger passenger = passengerDummy.passengers.get(id);
        return new ResponseEntity<PassengerResponseDTO>(passenger.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/activate/{activationId}")
    @ResponseStatus(HttpStatus.OK)
    public String activatePassenger(@PathVariable("activationId") Long id) {
        Passenger passenger = passengerDummy.passengers.get(id);
        return "Successful account activation";
    }

    @PutMapping (value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassengerResponseDTO> updatePassenger(@PathVariable("id") Long id, @RequestBody CreatePassengerDTO passengerDTO) {
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
        return new ResponseEntity<PassengerResponseDTO>(passengerForUpdate.parseToResponse(), HttpStatus.OK);
    }
}
