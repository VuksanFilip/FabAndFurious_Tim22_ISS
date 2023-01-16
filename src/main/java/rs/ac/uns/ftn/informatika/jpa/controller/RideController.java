package rs.ac.uns.ftn.informatika.jpa.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.ValidateData;
import rs.ac.uns.ftn.informatika.jpa.dto.messages.MessageDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestFavoriteLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestPanicStringDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestRejectionLetterDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestRideDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseFavoriteLocationsDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseRideDTO;
import rs.ac.uns.ftn.informatika.jpa.model.*;
import rs.ac.uns.ftn.informatika.jpa.model.enums.RideStatus;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IFavoriteLocationsService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IPanicService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IPassengerService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IRideService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ride")
public class RideController{

    private ValidateData validateData= new ValidateData();
    private IRideService rideService;
    private IPanicService panicService;
    private IFavoriteLocationsService favouriteLocationService;
    private IPassengerService passengerService;

    @Autowired
    public RideController(IRideService rideService, IPanicService panicService, IFavoriteLocationsService favouriteLocationService, IPassengerService passengerService) {
        this.rideService = rideService;
        this.panicService = panicService;
        this.favouriteLocationService = favouriteLocationService;
        this.passengerService = passengerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postRide(@RequestBody RequestRideDTO requestRideDTO){

        List<Passenger> pendingPassengers = passengerService.getAllWithStatusPending();
        for(Passenger ridePassenger : requestRideDTO.getPassengers()){
            for(Passenger pendingPassenger: pendingPassengers){
                if(ridePassenger.getId() == pendingPassenger.getId()){
                    return new ResponseEntity<>("Cannot create a ride while you have one already pending!", HttpStatus.BAD_REQUEST);
                }
            }
        }

        //TODO SKONTATI JOS KAKO BIRAMO DRIVERA(JA SAM PRAVIO NOVOG)
        Ride ride = requestRideDTO.parseToRide();
        rideService.add(ride);
        return new ResponseEntity<>(ride.parseToResponseDefault(), HttpStatus.OK);
    }

    @GetMapping(value = "/driver/{driverId}/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getActiveDriver(@PathVariable("driverId") String id) {

        Ride ride = rideService.getaActiveRideByDriverId(id);
        if(ride != null){
            ResponseRideDTO response = ride.parseToResponseDefault();
//            response.setDefaultResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("Active ride does not exist!", HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/passenger/{passengerId}/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getActivePassenger(@PathVariable("passengerId") String id) {

        Ride ride = rideService.getActiveRideByPassengerId(id);
        if(ride != null){
            ResponseRideDTO response = ride.parseToResponseDefault();
//            response.setDefaultResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("Active ride does not exist!", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRide(@PathVariable("id") String id) {

        if(!validateData.isNumericOrNegativeLong(id)){
            return new ResponseEntity<>(new MessageDTO("Invalid data. For example bad Id format."), HttpStatus.BAD_REQUEST);
        }
        else if(!rideService.existsById(id)){
            return new ResponseEntity<>("Ride does not exist!", HttpStatus.NOT_FOUND);
        }
        Ride ride = rideService.getRide(id).get();
        return new ResponseEntity<>(ride.parseToResponseWithStatus(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> withdrawRide(@PathVariable String id){

        if(!rideService.existsById(id)){
            return new ResponseEntity<>("Ride does not exist!", HttpStatus.NOT_FOUND);
        }
        Ride ride = rideService.getRide(id).get();
        if((ride.getStatus() != RideStatus.PENDING) && (ride.getStatus() != RideStatus.STARTED)){
            return new ResponseEntity<>(new MessageDTO("Cannot cancel a ride that is not in status PENDING or STARTED!"), HttpStatus.NOT_FOUND);

        }
        rideService.updateRideByStatus(id, RideStatus.CANCELED);
        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponseDefault(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/panic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> setPanicReason(@RequestBody RequestPanicStringDTO reason, @PathVariable String id) throws Exception {

        if(!rideService.existsById(id)){
            return new ResponseEntity<>("Ride does not exist", HttpStatus.NOT_FOUND);
        }
        Optional<Ride> ride = rideService.getRide(id);
        Panic panic = panicService.createPanicByRide(ride.get(),reason.getReason());

        panicService.add(panic);
        return new ResponseEntity<>(panic.parseToResponseSmallerData(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/start", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> startRide(@PathVariable String id){

        if(!rideService.existsById(id)){
            return new ResponseEntity<>("Ride does not exist!", HttpStatus.NOT_FOUND);
        }
        Ride ride = rideService.getRide(id).get();
        if((ride.getStatus() != RideStatus.ACCEPTED)){
            return new ResponseEntity<>(new MessageDTO("Cannot start a ride that is not in status ACCEPTED!"), HttpStatus.NOT_FOUND);

        }
        rideService.updateRideByStatus(id, RideStatus.STARTED);
        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponseDefault(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/accept", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> acceptRide(@PathVariable String id){

        if(!rideService.existsById(id)){
            return new ResponseEntity<>("Ride does not exist!", HttpStatus.NOT_FOUND);
        }
        Ride ride = rideService.getRide(id).get();
        if((ride.getStatus() != RideStatus.PENDING)){
            return new ResponseEntity<>(new MessageDTO("Cannot start a ride that is not in status PENDING!"), HttpStatus.NOT_FOUND);

        }
        rideService.updateRideByStatus(id, RideStatus.ACCEPTED);
        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponseDefault(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/end", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> endRide(@PathVariable String id) {

        if(!rideService.existsById(id)){
            return new ResponseEntity<>("Ride does not exist!", HttpStatus.NOT_FOUND);
        }
        Ride ride = rideService.getRide(id).get();
        if((ride.getStatus() != RideStatus.FINISHED)){
            return new ResponseEntity<>(new MessageDTO("Cannot end a ride that is not in status FINISHED!"), HttpStatus.NOT_FOUND);
        }

        rideService.updateRideByStatus(id, RideStatus.FINISHED);
        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponseDefault(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/cancel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancelRide(@RequestBody RequestRejectionLetterDTO letter, @PathVariable String id) {

        if(!rideService.existsById(id)){
            return new ResponseEntity<>("Ride does not exist!", HttpStatus.NOT_FOUND);
        }
        Ride ride = rideService.getRide(id).get();
        if((ride.getStatus() != RideStatus.PENDING)){
            return new ResponseEntity<>(new MessageDTO("Cannot cancel a ride that is not in status PENDING!"), HttpStatus.NOT_FOUND);
        }
        RejectionLetter rejectionLetter = letter.parseToRejectionLetter();

        rideService.updateRideByRejectionLetter(id, rejectionLetter);
        rideService.updateRideByStatus(id, RideStatus.FINISHED);

        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponseWithStatusAndReason(), HttpStatus.OK);
    }

    @PostMapping(value = "/favorites", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postFavouriteLocation(@RequestBody RequestFavoriteLocationDTO requestFavouriteLocationDTO){

        FavoriteRoute favoriteRoute = requestFavouriteLocationDTO.parseToFavoriteLocations();
        List<Passenger> passengers = favoriteRoute.getPassengers();
        for(Passenger p : passengers){
            Passenger passenger = passengerService.getPassenger((p.getId()).toString()).get();
            if(passenger.getFavoriteLocations().size()+1 > 10){
                    return new ResponseEntity<>(new MessageDTO("Number of favorite rides cannot exceed 10!"), HttpStatus.BAD_REQUEST);
            }
        }

        favouriteLocationService.add(favoriteRoute);
        return new ResponseEntity<>(favoriteRoute.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/favorites", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFavoriteLocations() {

        List<FavoriteRoute> favoriteLocations = favouriteLocationService.getAll();
        List<ResponseFavoriteLocationsDTO> responseFavoriteLocations = new FavoriteRoute().parseToResponseList(favoriteLocations);

        return new ResponseEntity<>(responseFavoriteLocations, HttpStatus.OK);
    }

    @DeleteMapping(value = "/favorites/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFavoriteLocations(@PathVariable("id") String id) {

        if(!favouriteLocationService.existsById(id)){
            return new ResponseEntity<>("Favorite location does not exist!", HttpStatus.NOT_FOUND);
        }

        favouriteLocationService.deleteById(id);
        return new ResponseEntity<>("Successful deletion of favorite location!", HttpStatus.NO_CONTENT);
    }
}
