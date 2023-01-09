package rs.ac.uns.ftn.informatika.jpa.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestFavoriteLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestPanicStringDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestRejectionLetterDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestRideDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseFavoriteLocationsDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePanicSmallerDataDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseRideDTO;
import rs.ac.uns.ftn.informatika.jpa.model.*;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.FavoriteLocationsService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.PanicService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.RideService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ride")
public class RideController{

    private RideService rideService;
    private PanicService panicService;
    private FavoriteLocationsService favouriteLocationService;

    @Autowired
    public RideController(RideService rideService, PanicService panicService, FavoriteLocationsService favouriteLocationService) {
        this.rideService = rideService;
        this.panicService = panicService;
        this.favouriteLocationService = favouriteLocationService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRideDTO> postRide(@RequestBody RequestRideDTO requestRideDTO){

        //TODO Skontati kako sam objekat da izgenerise id preko baze
        long size = rideService.getSize()+1;

        Ride ride = requestRideDTO.parseToRide(size);

        ride.setDriver(new Driver());
        ride.setLetter(new RejectionLetter());

        rideService.add(ride);

        ResponseRideDTO responseRideDTO = ride.parseToResponseDefault();
        return new ResponseEntity<>(responseRideDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/driver/{driverId}/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRideDTO> getActiveDriver(@PathVariable("driverId") String id) {

        Ride ride = rideService.getRideByDriverId(id);
        if(ride != null){
            return new ResponseEntity<>(ride.parseToResponseDefault(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/passenger/{passengerId}/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRideDTO> getActivePassenger(@PathVariable("passengerId") String id) {

        Ride ride = rideService.getRideByPassengerId(id);
        if(ride != null){
            return new ResponseEntity<>(ride.parseToResponseDefault(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRideDTO> getActiveRide(@PathVariable("id") String id) {

        Optional<Ride> ride = rideService.getRide(id);
        if (ride == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ride.get().parseToResponseWithStatus(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRideDTO> withdrawRide(@PathVariable String id) throws Exception {

        rideService.updateRideByStatus(id, RideStatus.CANCELED);
        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponseDefault(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/accept", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRideDTO> acceptRide(@PathVariable String id) throws Exception {

        rideService.updateRideByStatus(id, RideStatus.ACCEPTED);
        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponseDefault(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/end", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRideDTO> endRide(@PathVariable String id) throws Exception {

        rideService.updateRideByStatus(id, RideStatus.FINISHED);
        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponseDefault(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/panic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePanicSmallerDataDTO> setPanicReason(@RequestBody RequestPanicStringDTO reason, @PathVariable String id) throws Exception {

        //TODO Skontati kako sam objekat da izgenerise id preko baze
        Optional<Ride> ride = rideService.getRide(id);
        Panic panic = panicService.createPanicByRide(ride.get(),reason.getReason());

        panicService.add(panic);
        return new ResponseEntity<>(panic.parseToResponseSmallerData(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/cancel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRideDTO> cancelRide(@RequestBody RequestRejectionLetterDTO letter, @PathVariable String id) throws Exception {

        RejectionLetter rejectionLetter = letter.parseToRejectionLetter();

        rideService.updateRideByRejectionLetter(id, rejectionLetter);
        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponseWithStatusAndReason(), HttpStatus.OK);
    }

    @PostMapping(value = "/favorites", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseFavoriteLocationsDTO> postFavouriteLocation(@RequestBody RequestFavoriteLocationDTO requestFavouriteLocationDTO){

        FavoriteLocations favoriteLocations = new FavoriteLocations();
        favoriteLocations.setFavoriteName(requestFavouriteLocationDTO.getFavoriteName());
        favoriteLocations.setLocations(requestFavouriteLocationDTO.getLocations());
        favoriteLocations.setPassengers(requestFavouriteLocationDTO.getPassengers());
        favoriteLocations.setVehicleType(requestFavouriteLocationDTO.getVehicleType());
        favoriteLocations.setPetTransport(requestFavouriteLocationDTO.isPetTransport());
        favoriteLocations.setBabyTransport(requestFavouriteLocationDTO.isBabyTransport());

        favoriteLocations = favouriteLocationService.add(favoriteLocations);
        return new ResponseEntity<>(favoriteLocations.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/favorites", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResponseFavoriteLocationsDTO>> getFavoriteLocations() {

        List<FavoriteLocations> favoriteLocations = favouriteLocationService.getAll();
        List<ResponseFavoriteLocationsDTO> responseFavoriteLocations = new FavoriteLocations().parseToResponseList(favoriteLocations);

        return new ResponseEntity<>(responseFavoriteLocations, HttpStatus.OK);
    }

    @DeleteMapping(value = "/favorites/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResponseFavoriteLocationsDTO>> getFavoriteLocations(@PathVariable("id") String id) {

        favouriteLocationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}