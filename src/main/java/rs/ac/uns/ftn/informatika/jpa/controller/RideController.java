package rs.ac.uns.ftn.informatika.jpa.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestPanicStringDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestRejectionLetterDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestRideDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePanicSmallerDataDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseRideDTO;
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
    public ResponseEntity<ResponseRideDTO> createRide(@RequestBody RequestRideDTO requestRideDTO) throws Exception {

        Long rideId = rideDummy.rideCounter.incrementAndGet();
        Ride ride = requestRideDTO.parseToRide(rideId);
        rideDummy.rides.put(rideId, ride);

        ResponseRideDTO responseRideDTO = ride.parseToResponseWithStatus();
        return new ResponseEntity<>(responseRideDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/driver/{driverId}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRideDTO> getActiveDriver(@PathVariable("driverId") Long id) {
        for(Ride r : rideDummy.rides.values()){
            if(r.getDriver().getId() == id){
                return new ResponseEntity<>(r.parseToResponse(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/passenger/{passengerId}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRideDTO> getActivePassenger(@PathVariable("passengerId") Long id) {
        for(Ride r : rideDummy.rides.values()){
            for(Passenger p : r.getPassengers()){
                if(p.getId() == id){
                    return new ResponseEntity<>(r.parseToResponse(), HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRideDTO> getActiveRide(@PathVariable("id") Long id) {
        Ride ride = rideDummy.rides.get(id);
        if (ride == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ride.parseToResponseWithStatus(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRideDTO> withdrawRide(@PathVariable Long id) throws Exception {
        Ride ride = rideDummy.rides.get(id);
        ride.setStatus(RideStatus.CANCELED);
        ResponseRideDTO responseRideDTO = ride.parseToResponseWithStatus();
        rideDummy.rides.put(id, ride);

        return new ResponseEntity<>(responseRideDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/panic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePanicSmallerDataDTO> setPanicReason(@RequestBody RequestPanicStringDTO reason, @PathVariable Long id) throws Exception {
        Long panicId = panicDummy.counter.incrementAndGet();
        Ride ride = rideDummy.rides.get(id);
        Panic panic = new Panic(panicId, new User(), ride, new Date(), reason.getReason());
        panicDummy.panics.put(panicId, panic);

        return new ResponseEntity<>(panic.parseToResponseSmallerData(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/accept", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRideDTO> acceptRide(@PathVariable Long id) throws Exception {
        Ride ride = rideDummy.rides.get(id);
        ride.setStatus(RideStatus.ACCEPTED);
        ResponseRideDTO responseRideDTO = ride.parseToResponseWithStatus();
        rideDummy.rides.put(id, ride);

        return new ResponseEntity<>(responseRideDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/end", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRideDTO> endRide(@PathVariable Long id) throws Exception {
        Ride ride = rideDummy.rides.get(id);
        ride.setStatus(RideStatus.FINISHED);
        ResponseRideDTO responseRideDTO = ride.parseToResponseWithStatus();
        rideDummy.rides.put(id, ride);

        return new ResponseEntity<>(responseRideDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/cancel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRideDTO> cancelRide(@RequestBody RequestRejectionLetterDTO letter, @PathVariable Long id) throws Exception {
        Ride ride = rideDummy.rides.get(id);
        RejectionLetter rejectionLetter = letter.parseToRejectionLetter();
        ride.setLetter(rejectionLetter);
        ResponseRideDTO responseRideDTO = ride.parseToResponseWithStatusAndReason();
        rideDummy.rides.put(id, ride);

        return new ResponseEntity<>(responseRideDTO, HttpStatus.OK);
    }
}

