package rs.ac.uns.ftn.informatika.jpa.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.create.CreatePanicStringDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.create.CreateRejectionLetterDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.create.CreateRideDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.PanicSmallerDataResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.RideResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dummy.PanicDummy;
import rs.ac.uns.ftn.informatika.jpa.dummy.RideDummy;
import rs.ac.uns.ftn.informatika.jpa.model.*;

import java.util.Date;

@RestController
@RequestMapping("/api/ride")
public class RideController{
    private RideDummy rideDummy = new RideDummy();
    private PanicDummy panicDummy = new PanicDummy();

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> createRide(@RequestBody CreateRideDTO ride) throws Exception {
        Long rideId = rideDummy.rideCounter.incrementAndGet();
        RideResponseDTO rideResponseDTO = ride.parseToResponse(rideId);
        rideDummy.rides.put(rideId, ride.parseToRide(rideId));
        return new ResponseEntity<RideResponseDTO>(rideResponseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/driver/{driverId}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> getActiveDriver(@PathVariable("driverId") Long id) {
        for(Ride r : rideDummy.rides.values()){
            if(r.getDriver().getId() == id){
                return new ResponseEntity<RideResponseDTO>(r.parseToResponse(), HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<RideResponseDTO>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/passenger/{passengerId}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> getActivePassenger(@PathVariable("passengerId") Long id) {
        for(Ride r : rideDummy.rides.values()){
            for(Passenger p : r.getPassengers()){
                if(p.getId() == id){
                    return new ResponseEntity<RideResponseDTO>(r.parseToResponse(), HttpStatus.CREATED);
                }
            }
        }
        return new ResponseEntity<RideResponseDTO>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> getActiveRide(@PathVariable("id") Long id) {
        Ride ride = rideDummy.rides.get(id);
        if (ride == null) {
            return new ResponseEntity<RideResponseDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<RideResponseDTO>(ride.parseToResponse(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/withdraw")
    public ResponseEntity<RideResponseDTO> withdrawRide(@PathVariable Long id) throws Exception {
        Ride ride = rideDummy.rides.get(id);
        ride.setStatus(RideStatus.CANCELED);
        RideResponseDTO rideResponseDTO = ride.parseToResponseWithStatus();
        rideDummy.rides.put(id, ride);

        return new ResponseEntity<RideResponseDTO>(rideResponseDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/panic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PanicSmallerDataResponseDTO> setPanicReason(@RequestBody CreatePanicStringDTO reason, @PathVariable Long id) throws Exception {
        Long panicId = panicDummy.counter.incrementAndGet();
        Ride ride = rideDummy.rides.get(id);
        Panic panic = new Panic(panicId, new User(), ride, new Date(), reason.getReason());
        panicDummy.panics.put(panicId, panic);

        return new ResponseEntity<PanicSmallerDataResponseDTO>(panic.parseToResponseSmallerData(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/accept")
    public ResponseEntity<RideResponseDTO> acceptRide(@PathVariable Long id) throws Exception {
        Ride ride = rideDummy.rides.get(id);
        ride.setStatus(RideStatus.ACCEPTED);
        RideResponseDTO rideResponseDTO = ride.parseToResponseWithStatus();
        rideDummy.rides.put(id, ride);

        return new ResponseEntity<RideResponseDTO>(rideResponseDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/end")
    public ResponseEntity<RideResponseDTO> endRide(@PathVariable Long id) throws Exception {
        Ride ride = rideDummy.rides.get(id);
        ride.setStatus(RideStatus.FINISHED);
        RideResponseDTO rideResponseDTO = ride.parseToResponseWithStatus();
        rideDummy.rides.put(id, ride);

        return new ResponseEntity<RideResponseDTO>(rideResponseDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/cancel")
    public ResponseEntity<RideResponseDTO> cancelRide(@RequestBody CreateRejectionLetterDTO letter, @PathVariable Long id) throws Exception {
        Ride ride = rideDummy.rides.get(id);
        RejectionLetter rejectionLetter = letter.parseToRejectionLetter();
        ride.setLetter(rejectionLetter);
        RideResponseDTO rideResponseDTO = ride.parseToResponseWithStatusAndReason();
        rideDummy.rides.put(id, ride);

        return new ResponseEntity<RideResponseDTO>(rideResponseDTO, HttpStatus.OK);
    }
}

