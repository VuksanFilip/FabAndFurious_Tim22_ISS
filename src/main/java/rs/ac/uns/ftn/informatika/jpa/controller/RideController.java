package rs.ac.uns.ftn.informatika.jpa.controller;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.messages.MessageDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestFavoriteRouteDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestPanicStringDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestRejectionLetterDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestRideDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseFavoriteRouteDTO;
import rs.ac.uns.ftn.informatika.jpa.model.*;
import rs.ac.uns.ftn.informatika.jpa.model.enums.RideStatus;
import rs.ac.uns.ftn.informatika.jpa.model.enums.VehicleName;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/ride")
public class RideController{

    private final IRideService rideService;
    private final IPanicService panicService;
    private final IFavoriteRouteService favoriteRouteService;
    private final IPassengerService passengerService;
    private final IDriverService driverService;
    private final IUserService userService;

    @Autowired
    public RideController(IRideService rideService, IPanicService panicService, IFavoriteRouteService favoriteRouteService, IPassengerService passengerService, IDriverService driverService, IUserService userService) {
        this.rideService = rideService;
        this.panicService = panicService;
        this.favoriteRouteService = favoriteRouteService;
        this.passengerService = passengerService;
        this.driverService = driverService;
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('PASSENGER')")
    public ResponseEntity<?> createNewRide(@Valid @RequestBody RequestRideDTO requestRideDTO){

//        String passengerId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId().toString();
//
//        if(!this.passengerService.existsById(passengerId) && !StringUtils.isNumeric(passengerId)){
//            return new ResponseEntity<>(new MessageDTO("PassengerId does not exist!"), HttpStatus.NOT_FOUND);
//        }
        Driver perfectDriver = this.driverService.getPerfectDriver(VehicleName.valueOf(requestRideDTO.getVehicleType()), requestRideDTO.getScheduledTime(), requestRideDTO.getLocations().get(0));
        if(perfectDriver == null){
            return new ResponseEntity<>(new MessageDTO("Recently there is no free drivers"), HttpStatus.NOT_FOUND);
        }

        Ride newRide = this.rideService.parseToRide(requestRideDTO, perfectDriver);

//        return new ResponseEntity<>(newRide.parseToResponse(), HttpStatus.OK);
        return new ResponseEntity<>(newRide.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/driver/{driverId}/active", produces = MediaType.APPLICATION_JSON_VALUE)
  //  @PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER')")
    public ResponseEntity<?> getActiveRideForDriver(@PathVariable("driverId") String id) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!this.driverService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("Driver does not exist!"), HttpStatus.NOT_FOUND);
        }
        Ride ride = rideService.getaActiveRideByDriverId(id);
        if(ride != null){
            return new ResponseEntity<>(ride.parseToResponse(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageDTO("Active ride does not exist!"), HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/passenger/{passengerId}/active", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PASSENGER')")
    public ResponseEntity<?> getActiveRideForPassenger(@PathVariable("passengerId") String id) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!this.passengerService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("Passenger does not exist!"), HttpStatus.NOT_FOUND);
        }
        Ride ride = rideService.getActiveRideByPassengerId(id);
        if(ride != null){
            return new ResponseEntity<>(ride.parseToResponse(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageDTO("Active ride does not exist!"), HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'PASSENGER')")
    public ResponseEntity<?> getRide(@PathVariable("id") String id) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        else if(!rideService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("Ride does not exist!"), HttpStatus.NOT_FOUND);
        }
        Ride ride = rideService.getRide(id).get();
        return new ResponseEntity<>(ride.parseToResponse(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
   // @PreAuthorize("hasAuthority('DRIVER')")
    public ResponseEntity<?> withdrawRide(@PathVariable String id){

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(rideService.checkIfNotPendingAndNotStartedById(id)){
            return new ResponseEntity<>(new MessageDTO("Cannot withdraw a ride that is not in status PENDING or STARTED!"), HttpStatus.BAD_REQUEST);
        }
        rideService.updateRideByStatus(id, RideStatus.REJECTED);
        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponse(), HttpStatus.OK);
    }

    //TODO IMA VEZE SA SEKJURITIJEM (PROMENITI USERA) "TESTIRATI"

    @PutMapping(value = "/{id}/panic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('DRIVER', 'PASSENGER')")
    public ResponseEntity<?> setPanicReason(@Valid @RequestBody RequestPanicStringDTO reason, @PathVariable String id) {

        String userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId().toString();

        if(!StringUtils.isNumeric(userId)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!this.userService.existsById(userId)){
            return new ResponseEntity<>(new MessageDTO("User does not exist!"), HttpStatus.NOT_FOUND);
        }
        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!rideService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("Ride does not exist"), HttpStatus.NOT_FOUND);
        }
        Ride ride = rideService.getRide(id).get();
        if(!rideService.checkIfPassengerExistInRide(id, userId) && !rideService.checkIfDriverExistInRide(id, userId)){
            return new ResponseEntity<>(new MessageDTO("Cannot crate panic for this ride"), HttpStatus.NOT_FOUND);
        }

        Panic panic = panicService.createPanicByRide(this.userService.getUser(userId).get(), ride, reason.getReason());

        panicService.add(panic);
        return new ResponseEntity<>(panic.parseToResponseSmallerData(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/start", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('DRIVER')")
    public ResponseEntity<?> startRide(@PathVariable String id){

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!rideService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("Ride does not exist!"), HttpStatus.NOT_FOUND);
        }
        if(rideService.checkIfNotAcceptedById(id)){
            return new ResponseEntity<>(new MessageDTO("Cannot start a ride that is not in status ACCEPTED!"), HttpStatus.BAD_REQUEST);
        }
        rideService.updateRideByStatus(id, RideStatus.STARTED);
        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponse(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/accept", produces = MediaType.APPLICATION_JSON_VALUE)
 //   @PreAuthorize("hasAuthority('DRIVER')")
    public ResponseEntity<?> acceptRide(@PathVariable String id){

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!rideService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("Ride does not exist!"), HttpStatus.NOT_FOUND);
        }
        if(rideService.checkIfNotPendingById(id)){
            return new ResponseEntity<>(new MessageDTO("Cannot start a ride that is not in status PENDING!"), HttpStatus.BAD_REQUEST);
        }
        rideService.updateRideByStatus(id, RideStatus.ACCEPTED);
        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponse(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/end", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('DRIVER')")
    public ResponseEntity<?> endRide(@PathVariable String id) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!rideService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("Ride does not exist!"), HttpStatus.NOT_FOUND);
        }
        if(rideService.checkIfNotStartedById(id)){
            return new ResponseEntity<>(new MessageDTO("Cannot endTime a ride that is not in status STARTED!"), HttpStatus.BAD_REQUEST);
        }
        rideService.updateRideByStatus(id, RideStatus.FINISHED);
        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponse(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/cancel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('DRIVER')")
    public ResponseEntity<?> cancelRide(@Valid @RequestBody RequestRejectionLetterDTO letter, @PathVariable String id) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!rideService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("Ride does not exist!"), HttpStatus.NOT_FOUND);
        }
        if(rideService.checkIfNotPendingAndNotAcceptedById(id)){
            return new ResponseEntity<>(new MessageDTO("Cannot cancel a ride that is not in status PENDING or ACCEPTED!"), HttpStatus.BAD_REQUEST);
        }
        RejectionLetter rejectionLetter = letter.parseToRejectionLetter();

        rideService.updateRideByRejectionLetterAndStatus(id, rejectionLetter, RideStatus.CANCELED);
        return new ResponseEntity<>(rideService.getRide(id).get().parseToResponse(), HttpStatus.OK);
    }

    @PostMapping(value = "/favorites", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PASSENGER')")
    public ResponseEntity<?> createFavoriteRoute(@Valid @RequestBody RequestFavoriteRouteDTO requestFavoriteRoute){

        List<Passenger> passengers = passengerService.getPassengersFromFavoriteRouteRequest(requestFavoriteRoute);
        if(passengerService.hasTenFavoriteRoutesForPassengers(passengers)){
            return new ResponseEntity<>(new MessageDTO("Number of favorite rides cannot exceed 10!"), HttpStatus.BAD_REQUEST);
        }
        FavoriteRoutes favoriteRoutes = this.favoriteRouteService.postFavoriteRoute(passengers, requestFavoriteRoute);
        return new ResponseEntity<>(favoriteRoutes.parseToResponseWithScheduledTime(), HttpStatus.OK);
    }

    @GetMapping(value = "/favorites", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PASSENGER')")
    public ResponseEntity<?> getFavoriteRoutes() {

        String userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId().toString();

        List<ResponseFavoriteRouteDTO> responseFavoriteRoutes = favoriteRouteService.getResponseFavoriteRoutes(userId);
        return new ResponseEntity<>(responseFavoriteRoutes, HttpStatus.OK);
    }

    @DeleteMapping(value = "/favorites/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PASSENGER')")
    public ResponseEntity<?> deleteFavoriteRoute(@PathVariable("id") String id) {

        if (!StringUtils.isNumeric(id)) {
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if (!this.favoriteRouteService.existsById(id)) {
            return new ResponseEntity<>(new MessageDTO("Favorite location does not exist!"), HttpStatus.NOT_FOUND);
        }
        favoriteRouteService.deleteFavouriteRoutesFromPassengers(id);
        favoriteRouteService.deleteById(id);
        return new ResponseEntity<>(new MessageDTO("Successful deletion of favorite location!"), HttpStatus.NO_CONTENT);
    }

//    @GetMapping(value = "/favorites/{passengerId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('PASSENGER')")
//    public ResponseEntity<?> getFavoriteOfPassenger(@PathVariable("passengerId") String passengerId, Pageable page) {
//
//        if(!StringUtils.isNumeric(passengerId)){
//            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
//        }
//        if(!this.passengerService.existsById(passengerId)){
//            return new ResponseEntity<>(new MessageDTO("Favorite location does not exist!"), HttpStatus.NOT_FOUND);
//        }
//
//        Page<FavoriteRoutes> favoriteRoutesPage = this.passengerService.findFavouriteRoutesByPassengerId(passengerId, page);
//
//        List<ResponseFavoriteRouteWithoutPassengersDTO> responseFavoriteRouteWithoutPassengersDTOS = new ArrayList<>();
//        for(FavoriteRoutes favorites : favoriteRoutesPage){
//            responseFavoriteRouteWithoutPassengersDTOS.add(favorites.parseToResponseWithoutPassengers());
//        }
//
//        return new ResponseEntity<>(new ResponsePageDTO(favoriteRoutesPage.getNumberOfElements(), Arrays.asList(responseFavoriteRouteWithoutPassengersDTOS.toArray())), HttpStatus.OK);
//    }
//
//    @DeleteMapping(value = "/favorites/{routeId}/{passengerId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('PASSENGER')")
//    public ResponseEntity<?> deleteFavoriteRouteOfPassenger(@PathVariable("routeId") String routeId, @PathVariable("passengerId") String passengerId) {
//
//        if(!StringUtils.isNumeric(routeId)){
//            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
//        }
//        if(!this.favoriteRouteService.existsById(routeId)){
//            return new ResponseEntity<>(new MessageDTO("Favorite location does not exist!"), HttpStatus.NOT_FOUND);
//        }
//        if(!StringUtils.isNumeric(passengerId)){
//            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
//        }
//        if(!this.passengerService.existsById(passengerId)){
//            return new ResponseEntity<>(new MessageDTO("Passenger does not exist!"), HttpStatus.NOT_FOUND);
//        }
//
//        FavoriteRoutes favoriteRoutes = favoriteRouteService.getFavoriteLocations(routeId).get();
//        Passenger passenger = this.passengerService.getPassenger(passengerId).get();
//        if(favoriteRoutes.getPassengers().contains(passenger)){
//            favoriteRoutes.getPassengers().remove(passenger);
//            passenger.getFavoriteRoutes().remove(favoriteRoutes);
//            this.passengerService.add(passenger);
//            this.favoriteRouteService.add(favoriteRoutes);
//            return new ResponseEntity<>(new MessageDTO("Successful deletion of favorite location!"), HttpStatus.NO_CONTENT);
//
//        }
//
//        return new ResponseEntity<>(new MessageDTO("Passenger dont have that favorite rute!"), HttpStatus.BAD_REQUEST);
//    }
}
