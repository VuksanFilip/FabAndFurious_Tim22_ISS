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
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IFavoriteLocationsService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IPanicService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IRideService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ride")
public class RideController{

    private IRideService rideService;
    private IPanicService panicService;
    private IFavoriteLocationsService favouriteLocationService;

    @Autowired
    public RideController(IRideService rideService, IPanicService panicService, IFavoriteLocationsService favouriteLocationService) {
        this.rideService = rideService;
        this.panicService = panicService;
        this.favouriteLocationService = favouriteLocationService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRideDTO> postRide(@RequestBody RequestRideDTO requestRideDTO){

        Ride ride = new Ride();
        ride.setLocations(requestRideDTO.getLocations());
        ride.setPassengers(requestRideDTO.getPassengers());
        ride.setVehicle(new Vehicle(new VehicleType(requestRideDTO.getVehicleType())));
        ride.setBabyTransport(requestRideDTO.isBabyTransport());
        ride.setPetTransport(requestRideDTO.isPetTransport());
        ride.setDriver(new Driver());
        ride.setLetter(new RejectionLetter());

        rideService.add(ride);
        return new ResponseEntity<>(ride.parseToResponseDefault(), HttpStatus.OK);
    }

    @GetMapping(value = "/driver/{driverId}/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRideDTO> getActiveDriver(@PathVariable("driverId") String id) {

        Ride ride = rideService.getRideByDriverId(id);
        if(ride != null){
            ResponseRideDTO response = ride.parseToResponseDefault();
//            response.setId(123L);
//            response.getStartTime();
//            response.getEndTime();
//            response.setTotalCost(12345);
//            response.getDriver().setId(123L);
//            response.getDriver().setEmail("user@example.com");
//            response.getPassengers().get(0).setId(123L);
//            response.getPassengers().get(0).setEmail("user@example.com");
//            response.setEstimatedTimeInMinutes(5);
//            response.setVehicleType(Type.STANDARD);
//            response.setBabyTransport(true);
//            response.setPetTransport(true);
//            response.getRejection().setReason("Ride is canceled due to previous problems with the passenger");
//            response.getRejection().getTimeOfRejection();
//            response.getLocations().get(0).getDeparture().setAddress("Bulevar oslobodjenja 46");
//            response.getLocations().get(0).getDeparture().setLatitude(45.267136);
//            response.getLocations().get(0).getDeparture().setLongitude(19.833549);
//            response.getLocations().get(0).getDestination().setAddress("Bulevar oslobodjenja 46");
//            response.getLocations().get(0).getDestination().setLatitude(45.267136);
//            response.getLocations().get(0).getDestination().setLongitude(19.833549);
//            response.setStatus(RideStatus.PENDING);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/passenger/{passengerId}/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseRideDTO> getActivePassenger(@PathVariable("passengerId") String id) {

        Ride ride = rideService.getRideByPassengerId(id);
        if(ride != null){
            ResponseRideDTO response = ride.parseToResponseDefault();
//            response.setId(123L);
//            response.getStartTime();
//            response.getEndTime();
//            response.setTotalCost(12345);
//            response.getDriver().setId(123L);
//            response.getDriver().setEmail("user@example.com");
//            response.getPassengers().get(0).setId(123L);
//            response.getPassengers().get(0).setEmail("user@example.com");
//            response.setEstimatedTimeInMinutes(5);
//            response.setVehicleType(Type.STANDARD);
//            response.setBabyTransport(true);
//            response.setPetTransport(true);
//            response.getRejection().setReason("Ride is canceled due to previous problems with the passenger");
//            response.getRejection().getTimeOfRejection();
//            response.getLocations().get(0).getDeparture().setAddress("Bulevar oslobodjenja 46");
//            response.getLocations().get(0).getDeparture().setLatitude(45.267136);
//            response.getLocations().get(0).getDeparture().setLongitude(19.833549);
//            response.getLocations().get(0).getDestination().setAddress("Bulevar oslobodjenja 46");
//            response.getLocations().get(0).getDestination().setLatitude(45.267136);
//            response.getLocations().get(0).getDestination().setLongitude(19.833549);
//            response.setStatus(RideStatus.PENDING);

            return new ResponseEntity<>(response, HttpStatus.OK);
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

        favouriteLocationService.add(favoriteLocations);
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