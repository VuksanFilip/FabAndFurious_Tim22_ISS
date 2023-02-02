package rs.ac.uns.ftn.informatika.jpa.controller;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.ValidateData;
import rs.ac.uns.ftn.informatika.jpa.dto.messages.MessageDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestFavoriteRouteDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestPanicStringDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestRejectionLetterDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestRideDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseFavoriteRouteDTO;
import rs.ac.uns.ftn.informatika.jpa.model.*;
import rs.ac.uns.ftn.informatika.jpa.model.enums.RideStatus;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/ride")
public class RideController{

    private final ValidateData validateData = new ValidateData();
    private final IRideService rideService;
    private final IPanicService panicService;
    private final IFavoriteRouteService favoriteRouteService;
    private final IPassengerService passengerService;
    private final IDriverService driverService;

    @Autowired
    public RideController(IRideService rideService, IPanicService panicService, IFavoriteRouteService favoriteRouteService, IPassengerService passengerService, IDriverService driverService) {
        this.rideService = rideService;
        this.panicService = panicService;
        this.favoriteRouteService = favoriteRouteService;
        this.passengerService = passengerService;
        this.driverService = driverService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('PASSENGER')")
    public ResponseEntity<?> createNewRide(@RequestBody RequestRideDTO requestRideDTO){
        Driver driver = this.driverService.getDriver("6").get();

        //TODO FALI PROVERA KADA ZAVRSAVA VOZAC RADNJU
//        Driver perfectDriver = this.driverService.getPerfectDriver(requestRideDTO.getVehicleType(), requestRideDTO.getScheduledTime(), requestRideDTO.getLocations().get(0));

        if(rideService.checkIfDriverHasPandingRides(driver)){
            return new ResponseEntity<>(new MessageDTO("Cannot create a ride while you have one already pending!"), HttpStatus.BAD_REQUEST);
        }
        driverService.getDriverByVehicleName(requestRideDTO.getVehicleType()).size();

        if(driverService.getDriverByVehicleName(requestRideDTO.getVehicleType()).size() == 0){
            return new ResponseEntity<>(new MessageDTO("Currently there are not that type of vehicles!"), HttpStatus.BAD_REQUEST);
        }
        Ride newRide = this.rideService.parseToRide(requestRideDTO, driver);
        return new ResponseEntity<>(newRide.parseToResponseNew(requestRideDTO.getScheduledTime()), HttpStatus.OK);
    }

    @GetMapping(value = "/driver/{driverId}/active", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER')")
    public ResponseEntity<?> getActiveRideForDriver(@PathVariable("driverId") String id) {

        Ride ride = rideService.getaActiveRideByDriverId(id);
        if(ride != null){
            return new ResponseEntity<>(ride.parseToResponseNew(new Date()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageDTO("Active ride does not exist!"), HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/passenger/{passengerId}/active", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'PASSENGER')")
    public ResponseEntity<?> getActiveRideForPassenger(@PathVariable("passengerId") String id) {

        Ride ride = rideService.getActiveRideByPassengerId(id);
        if(ride != null){
            return new ResponseEntity<>(ride.parseToResponseNew(new Date()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageDTO("Active ride does not exist!"), HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER', 'PASSENGER')")
    public ResponseEntity<?> getRide(@PathVariable("id") String id) {

        if(!validateData.isNumericOrNegativeLong(id)){
            return new ResponseEntity<>(new MessageDTO("Invalid data. For example bad Id format."), HttpStatus.BAD_REQUEST);
        }
        else if(!rideService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("Ride does not exist!"), HttpStatus.NOT_FOUND);
        }
        Ride ride = rideService.getRide(id).get();
        return new ResponseEntity<>(ride.parseToResponseNew(new Date()), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('PASSENGER')")
    public ResponseEntity<?> withdrawRide(@PathVariable String id){

        if(!rideService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("Ride does not exist!"), HttpStatus.NOT_FOUND);
        }
        if(rideService.checkIfNotPendingAndNotStartedById(id)){
            return new ResponseEntity<>(new MessageDTO("Cannot cancel a ride that is not in status PENDING or STARTED!"), HttpStatus.BAD_REQUEST);
        }
        rideService.updateRideByStatus(id, RideStatus.CANCELED);
        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponseNew(new Date()), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/panic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('DRIVER', 'PASSENGER')")
    public ResponseEntity<?> setPanicReason(@RequestBody RequestPanicStringDTO reason, @PathVariable String id) throws Exception {

        if(!rideService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("Ride does not exist"), HttpStatus.NOT_FOUND);
        }
        Ride ride = rideService.getRide(id).get();
        //TODO promeniti usera
        Panic panic = panicService.createPanicByRide(this.driverService.getDriver("5").get(), ride, reason.getReason());

        panicService.add(panic);
        return new ResponseEntity<>(panic.parseToResponseSmallerData(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/start", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('DRIVER')")
    public ResponseEntity<?> startRide(@PathVariable String id){

        if(!rideService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("Ride does not exist!"), HttpStatus.NOT_FOUND);
        }
        if(rideService.checkIfNotAcceptedById(id)){
            return new ResponseEntity<>(new MessageDTO("Cannot start a ride that is not in status ACCEPTED!"), HttpStatus.BAD_REQUEST);
        }
        rideService.updateRideByStatus(id, RideStatus.STARTED);
        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponseNew(new Date()), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/accept", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('DRIVER')")
    public ResponseEntity<?> acceptRide(@PathVariable String id){

        if(!rideService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("Ride does not exist!"), HttpStatus.NOT_FOUND);
        }
        if(rideService.checkIfNotPendingById(id)){
            return new ResponseEntity<>(new MessageDTO("Cannot start a ride that is not in status PENDING!"), HttpStatus.BAD_REQUEST);
        }
        rideService.updateRideByStatus(id, RideStatus.ACCEPTED);
        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponseNew(new Date()), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/end", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('DRIVER')")
    public ResponseEntity<?> endRide(@PathVariable String id) {

        if(!rideService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("Ride does not exist!"), HttpStatus.NOT_FOUND);
        }
        if(rideService.checkIfNotStartedById(id)){
            return new ResponseEntity<>(new MessageDTO("Cannot endTime a ride that is not in status STARTED!"), HttpStatus.BAD_REQUEST);
        }
        rideService.updateRideByStatus(id, RideStatus.FINISHED);
        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponseNew(new Date()), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/cancel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('DRIVER')")
    public ResponseEntity<?> cancelRide(@RequestBody RequestRejectionLetterDTO letter, @PathVariable String id) {

        if(!rideService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("Ride does not exist!"), HttpStatus.NOT_FOUND);
        }
        if(rideService.checkIfNotPendingAndNotAcceptedById(id)){
            return new ResponseEntity<>(new MessageDTO("Cannot cancel a ride that is not in status PENDING or ACCEPTED!"), HttpStatus.BAD_REQUEST);
        }
        RejectionLetter rejectionLetter = letter.parseToRejectionLetter();

        rideService.updateRideByRejectionLetterAndStatus(id, rejectionLetter, RideStatus.CANCELED);
        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponseNew(new Date()), HttpStatus.OK);
    }

    @PostMapping(value = "/favorites", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('PASSENGER')")
    public ResponseEntity<?> createFavoriteRoute(@RequestBody RequestFavoriteRouteDTO requestFavoriteRoute){

        List<Passenger> passengers = passengerService.getPassengersFromFavoriteRouteRequest(requestFavoriteRoute);
        if(passengerService.hasTenFavoriteRoutesForPassengers(passengers)){
            return new ResponseEntity<>(new MessageDTO("Number of favorite rides cannot exceed 10!"), HttpStatus.BAD_REQUEST);
        }
        FavoriteRoutes favoriteRoutes = this.favoriteRouteService.postFavoriteRoute(passengers, requestFavoriteRoute);
        return new ResponseEntity<>(favoriteRoutes.parseToResponseWithScheduledTime(), HttpStatus.OK);
    }

    @GetMapping(value = "/favorites", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('PASSENGER')")
    public ResponseEntity<?> getFavoriteRoutes() {

        List<ResponseFavoriteRouteDTO> responseFavoriteRoutes = favoriteRouteService.getResponseFavoriteRoutes();
        return new ResponseEntity<>(responseFavoriteRoutes, HttpStatus.OK);
    }

    @DeleteMapping(value = "/favorites/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('PASSENGER')")
    public ResponseEntity<?> deleteFavoriteRoute(@PathVariable("id") String id) {

        if(!this.favoriteRouteService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("Favorite location does not exist!"), HttpStatus.NOT_FOUND);
        }
        favoriteRouteService.deleteFavouriteRoutesFromPassengers(id);
        favoriteRouteService.deleteById(id);
        return new ResponseEntity<>(new MessageDTO("Successful deletion of favorite location!"), HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/favorites/{passengerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('PASSENGER')")
    public ResponseEntity<?> getFavoriteOfPassenger(@PathVariable("passengerId") String passengerId) {

        if(!StringUtils.isNumeric(passengerId)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!this.passengerService.existsById(passengerId)){
            return new ResponseEntity<>(new MessageDTO("Favorite location does not exist!"), HttpStatus.NOT_FOUND);
        }
        List<ResponseFavoriteRouteDTO> responseFavoriteRoutes = favoriteRouteService.getResponseFavoriteRoutes();
        return new ResponseEntity<>(responseFavoriteRoutes, HttpStatus.OK);
    }
}
