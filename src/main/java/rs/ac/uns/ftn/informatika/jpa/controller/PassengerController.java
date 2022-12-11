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
        return new ResponseEntity<PassengerResponseDTO>(passengerResponse, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PassengerResponseDTO> getPassenger(@PathVariable("id") Long id) {
        Passenger passenger = passengerDummy.passengers.get(id);
        if (passenger == null) {
            return new ResponseEntity<PassengerResponseDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<PassengerResponseDTO>(passenger.parseToResponse(), HttpStatus.OK);
    }

}
