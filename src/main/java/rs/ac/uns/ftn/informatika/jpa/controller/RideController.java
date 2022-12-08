package rs.ac.uns.ftn.informatika.jpa.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.CreateRideDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.RideResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dummy.RideDummy;

@RestController
@RequestMapping("/api/ride")
public class RideController{
    private RideDummy rideDummy = new RideDummy();

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RideResponseDTO> createRide(@RequestBody CreateRideDTO ride) throws Exception {
        RideResponseDTO rideResponseDTO = ride.parseToResponse();
        return new ResponseEntity<RideResponseDTO>(rideResponseDTO, HttpStatus.CREATED);
    }
}
