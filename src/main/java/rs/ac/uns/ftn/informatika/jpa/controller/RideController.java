package rs.ac.uns.ftn.informatika.jpa.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.CreateRideDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.RideResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.RideResponseRejectionDTO;
import rs.ac.uns.ftn.informatika.jpa.dummy.RideDummy;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;

@RestController
@RequestMapping("/api/ride")
public class RideController{
    private RideDummy rideDummy = new RideDummy();

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> createRide(@RequestBody CreateRideDTO ride) throws Exception {
        Long id = rideDummy.rideCounter.incrementAndGet();
        RideResponseDTO rideResponseDTO = ride.parseToResponse();
        rideDummy.rides.put(id, ride.parseToRide(id));
        return new ResponseEntity<RideResponseDTO>(rideResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/active/{driverId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> getActiveDriver(@PathVariable("driverId") Long id) {
        Ride ride = rideDummy.rides.get(id);
        if (ride == null) {
            return new ResponseEntity<RideResponseDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<RideResponseDTO>(ride.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/active/{passengerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> getActivePassenger(@PathVariable("passengerId") Long id) {
        Ride ride = rideDummy.rides.get(id);
        if (ride == null) {
            return new ResponseEntity<RideResponseDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<RideResponseDTO>(ride.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseRejectionDTO> getActiveRide(@PathVariable("id") Long id) {
        Ride ride = rideDummy.rides.get(id);
        if (ride == null) {
            return new ResponseEntity<RideResponseRejectionDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<RideResponseRejectionDTO>(ride.parseToResponseRejection(), HttpStatus.OK);
    }
}
