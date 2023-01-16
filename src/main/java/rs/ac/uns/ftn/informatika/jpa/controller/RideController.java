package rs.ac.uns.ftn.informatika.jpa.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.ValidateData;
import rs.ac.uns.ftn.informatika.jpa.dto.messages.MessageDTO;
import rs.ac.uns.ftn.informatika.jpa.model.enums.RideStatus;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.*;
import rs.ac.uns.ftn.informatika.jpa.model.*;
import rs.ac.uns.ftn.informatika.jpa.dto.request.*;
import rs.ac.uns.ftn.informatika.jpa.dto.response.*;

import java.util.ArrayList;
import java.util.Date;
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
    private IDriverService driverService;
    private ILocationService locationService;
    private IRouteService routeService;
    private IUserService userService;

    @Autowired
    public RideController(IRideService rideService, IPanicService panicService, IFavoriteLocationsService favouriteLocationService, IPassengerService passengerService, IDriverService driverService, ILocationService locationService, IRouteService routeService, IUserService userService) {
        this.rideService = rideService;
        this.panicService = panicService;
        this.favouriteLocationService = favouriteLocationService;
        this.passengerService = passengerService;
        this.driverService = driverService;
        this.locationService = locationService;
        this.routeService = routeService;
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postRide(@RequestBody RequestRideDTO requestRideDTO){
        Driver driver = this.driverService.getDriver("6").get();
        List<Ride> allRidesOfDriver = this.rideService.getRidesOfDriver(driver);
        for(Ride r : allRidesOfDriver){
            if(r.getStatus() == RideStatus.PENDING){
                return new ResponseEntity<>(new MessageDTO("Cannot create a ride while you have one already pending!"), HttpStatus.BAD_REQUEST);
            }
        }
        List<Route> routes = new ArrayList<>();
        for(RequestLocationDTO l : requestRideDTO.getLocations()){
            Location l1 = new Location(l.getDeparture().getAddress(), l.getDeparture().getLatitude(), l.getDeparture().getLongitude());
            Location l2 = new Location(l.getDestination().getAddress(), l.getDestination().getLatitude(), l.getDestination().getLongitude());
            this.locationService.add(l1);
            this.locationService.add(l2);
            Route r = new Route(l1, l2);
            this.routeService.add(r);
            routes.add(r);
        }
        List<Passenger> passengers = new ArrayList<>();
        for(ResponsePassengerIdEmailDTO p : requestRideDTO.getPassengers()){
            passengers.add(this.passengerService.findByEmail(p.getEmail()));
        }
        Ride newRide = new Ride(driver, passengers, routes, requestRideDTO.isBabyTransport(), requestRideDTO.isPetTransport());
        this.rideService.add(newRide);
        driver.getRides().add(newRide);
        this.driverService.add(driver);
        return new ResponseEntity<>(newRide.parseToResponseNew(requestRideDTO.getScheduledTime()), HttpStatus.OK);
    }

    @GetMapping(value = "/driver/{driverId}/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getActiveDriver(@PathVariable("driverId") String id) {

        Ride ride = rideService.getaActiveRideByDriverId(id);
        if(ride != null){
            return new ResponseEntity<>(ride.parseToResponseNew(new Date()), HttpStatus.OK);
        }
        return new ResponseEntity<>("Active ride does not exist!", HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/passenger/{passengerId}/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getActivePassenger(@PathVariable("passengerId") String id) {

        Ride ride = rideService.getActiveRideByPassengerId(id);
        if(ride != null){
            return new ResponseEntity<>(ride.parseToResponseNew(new Date()), HttpStatus.OK);
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
        return new ResponseEntity<>(ride.parseToResponseNew(new Date()), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> withdrawRide(@PathVariable String id){

        if(!rideService.existsById(id)){
            return new ResponseEntity<>("Ride does not exist!", HttpStatus.NOT_FOUND);
        }
        Ride ride = rideService.getRide(id).get();
        if((ride.getStatus() != RideStatus.PENDING) && (ride.getStatus() != RideStatus.STARTED)){
            return new ResponseEntity<>(new MessageDTO("Cannot cancel a ride that is not in status PENDING or STARTED!"), HttpStatus.BAD_REQUEST);

        }
        rideService.updateRideByStatus(id, RideStatus.CANCELED);
        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponseNew(new Date()), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/panic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> setPanicReason(@RequestBody RequestPanicStringDTO reason, @PathVariable String id) throws Exception {

        if(!rideService.existsById(id)){
            return new ResponseEntity<>("Ride does not exist", HttpStatus.NOT_FOUND);
        }
        Optional<Ride> ride = rideService.getRide(id);
        //TODO promeniti usera
        Panic panic = panicService.createPanicByRide(this.driverService.getDriver("5").get(), ride.get(), reason.getReason());

        panicService.add(panic);
        return new ResponseEntity<>(panic.parseToResponseSmallerData(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/start", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> startRide(@PathVariable String id){

        if(!rideService.existsById(id)){
            return new ResponseEntity<>("Ride does not exist!", HttpStatus.NOT_FOUND);
        }
        Ride ride = rideService.getRide(id).get();
        if(ride.getStatus() != RideStatus.ACCEPTED){
            return new ResponseEntity<>(new MessageDTO("Cannot start a ride that is not in status ACCEPTED!"), HttpStatus.BAD_REQUEST);

        }
        rideService.updateRideByStatus(id, RideStatus.STARTED);
        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponseNew(new Date()), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/accept", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> acceptRide(@PathVariable String id){

        if(!rideService.existsById(id)){
            return new ResponseEntity<>("Ride does not exist!", HttpStatus.NOT_FOUND);
        }
        Ride ride = rideService.getRide(id).get();
        if(ride.getStatus() != RideStatus.PENDING){
            return new ResponseEntity<>(new MessageDTO("Cannot start a ride that is not in status PENDING!"), HttpStatus.BAD_REQUEST);

        }
        rideService.updateRideByStatus(id, RideStatus.ACCEPTED);
        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponseNew(new Date()), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/end", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> endRide(@PathVariable String id) {

        if(!rideService.existsById(id)){
            return new ResponseEntity<>("Ride does not exist!", HttpStatus.NOT_FOUND);
        }
        Ride ride = rideService.getRide(id).get();
        if((ride.getStatus() != RideStatus.STARTED)){
            return new ResponseEntity<>(new MessageDTO("Cannot end a ride that is not in status STARTED!"), HttpStatus.BAD_REQUEST);
        }

        rideService.updateRideByStatus(id, RideStatus.FINISHED);
        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponseNew(new Date()), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/cancel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancelRide(@RequestBody RequestRejectionLetterDTO letter, @PathVariable String id) {

        if(!rideService.existsById(id)){
            return new ResponseEntity<>("Ride does not exist!", HttpStatus.NOT_FOUND);
        }
        Ride ride = rideService.getRide(id).get();
        if((ride.getStatus() != RideStatus.PENDING) && (ride.getStatus() != RideStatus.ACCEPTED)){
            return new ResponseEntity<>(new MessageDTO("Cannot cancel a ride that is not in status PENDING or ACCEPTED!"), HttpStatus.BAD_REQUEST);
        }
        RejectionLetter rejectionLetter = letter.parseToRejectionLetter();

        rideService.updateRideByRejectionLetter(id, rejectionLetter);
        rideService.updateRideByStatus(id, RideStatus.REJECTED);

        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponseNew(new Date()), HttpStatus.OK);
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
