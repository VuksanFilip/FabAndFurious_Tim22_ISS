package rs.ac.uns.ftn.informatika.jpa.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.create.CreateRideDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.RideResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dummy.PanicDummy;
import rs.ac.uns.ftn.informatika.jpa.dummy.RideDummy;
import rs.ac.uns.ftn.informatika.jpa.model.Panic;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.model.User;

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
        return new ResponseEntity<RideResponseDTO>(rideResponseDTO, HttpStatus.CREATED);
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

    @PutMapping(value = "/{rideId}/panic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Panic> setPanicReason(@RequestBody String reason, @PathVariable Long rideId) throws Exception {
        Long panicId = panicDummy.counter.incrementAndGet();
        Ride ride = rideDummy.rides.get(rideId);
        Panic panic = new Panic(panicId, new User(), ride, new Date(), reason);
        panicDummy.panics.put(panicId, panic);

        return new ResponseEntity<Panic>(panic, HttpStatus.OK);
    }

    @PutMapping(value = "/{rideId}/accept")
    public ResponseEntity<RideResponseDTO> acceptRide(@PathVariable Long rideId) throws Exception {
        Ride ride = rideDummy.rides.get(rideId);
        ride.setStatus("ACCEPTED");
        RideResponseDTO rideResponseDTO = ride.parseToResponse();
        rideDummy.rides.put(rideId, ride);

        return new ResponseEntity<RideResponseDTO>(rideResponseDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{rideId}/end")
    public ResponseEntity<RideResponseDTO> endRide(@PathVariable Long rideId) throws Exception {
        Ride ride = rideDummy.rides.get(rideId);
        ride.setStatus("FINISHED");
        RideResponseDTO rideResponseDTO = ride.parseToResponse();
        rideDummy.rides.put(rideId, ride);

        return new ResponseEntity<RideResponseDTO>(rideResponseDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{rideId}/cancel")
    public ResponseEntity<RideResponseDTO> cancelRide(@PathVariable Long rideId) throws Exception {
        Ride ride = rideDummy.rides.get(rideId);
        ride.setStatus("REJECTED");
        RideResponseDTO rideResponseDTO = ride.parseToResponse();
        rideDummy.rides.put(rideId, ride);

        return new ResponseEntity<RideResponseDTO>(rideResponseDTO, HttpStatus.OK);
    }
}

