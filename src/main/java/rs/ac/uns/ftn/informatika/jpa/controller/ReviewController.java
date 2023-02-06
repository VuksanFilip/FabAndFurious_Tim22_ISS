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
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestReviewDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePageDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseReviewDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseRideReviewsDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Review;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.model.Vehicle;
import rs.ac.uns.ftn.informatika.jpa.model.enums.ReviewType;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private final IRideService rideService;
    private final IReviewService reviewService;
    private final IVehicleService vehicleService;
    private final IPassengerService passengerService;
    private final IDriverService driverService;


    @Autowired
    public ReviewController(IReviewService reviewService, IVehicleService vehicleService, IRideService rideService, IPassengerService passengerService, IDriverService driverService) {
        this.reviewService = reviewService;
        this.vehicleService = vehicleService;
        this.rideService = rideService;
        this.passengerService = passengerService;
        this.driverService = driverService;
    }

    //TODO IMA VEZE SA SEKJURITIJEM (PROMENITI PASSENGERA), "TESTIRATI"

    @PostMapping(value = "{rideId}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('PASSENGER')")
    public ResponseEntity<?> createVehicleReview(@PathVariable("rideId") String rideId, @Valid @RequestBody RequestReviewDTO requestReviewDTO) {

        String passengerId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId().toString();
        System.out.println(passengerId);

        if(!StringUtils.isNumeric(passengerId)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!this.passengerService.existsById(passengerId)){
            return new ResponseEntity<>(new MessageDTO("PassengerId does not exist!"), HttpStatus.NOT_FOUND);
        }
        if(!StringUtils.isNumeric(rideId)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!this.rideService.existsById(rideId)){
            return new ResponseEntity<>(new MessageDTO("Ride does not exist!"), HttpStatus.NOT_FOUND);
        }
        if(this.rideService.getRide(rideId).get().getVehicle() == null){
            return new ResponseEntity<>(new MessageDTO("Vehicle does not exist!"), HttpStatus.NOT_FOUND);
        }
        if(!this.rideService.checkIfPassengerExistInRide(rideId, passengerId)){
            return new ResponseEntity<>(new MessageDTO("Passenger is not in that ride!"), HttpStatus.NOT_FOUND);
        }


        Ride ride = this.rideService.getRide(rideId).get();
        Review review = new Review(requestReviewDTO.getRating(), requestReviewDTO.getComment(), ride, this.passengerService.getPassenger(passengerId).get(), ReviewType.VEHICLE);
        this.reviewService.add(review);
        ride.getReviews().add(review);
        this.rideService.add(ride);
        Vehicle vehicle = this.vehicleService.getVehicle(ride.getVehicle().getId().toString()).get();
        vehicle.getReviews().add(review);
        this.vehicleService.add(vehicle);
        return new ResponseEntity<>(review.parseToResponse(), HttpStatus.OK);
    }

    //RADI (NA SWAGGERU NE PISE DA TREBA PAGEABLE TAKO DA NIJE PAGEABLE)
    @GetMapping(value = "/vehicle/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'PASSENGER')")
    public ResponseEntity<?> getVehicleReviews(@PathVariable("id") String id) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!this.vehicleService.getVehicle(id).isPresent()){
            return new ResponseEntity<>(new MessageDTO("Vehicle does not exist"), HttpStatus.NOT_FOUND);
        }

//        Vehicle vehicle = this.vehicleService.getVehicle(id).get();
//
//        List<ResponseReviewDTO> responseReviewDTOS = new ArrayList<>();
//        for(Review r : vehicle.getReviews()){
//            responseReviewDTOS.add(r.parseToResponse());
//        }

        List<Review> reviewsForVehicle = this.reviewService.getReviewsForVehicle(id);

        List<ResponseReviewDTO> responseReviewDTOS = new ArrayList<>();
        for(Review r : reviewsForVehicle){
            responseReviewDTOS.add(r.parseToResponse());
        }
        return new ResponseEntity<>(new ResponsePageDTO(responseReviewDTOS.size(), Arrays.asList(responseReviewDTOS.toArray())), HttpStatus.OK);
    }


    //TODO IMA VEZE SA SEKJURITIJEM (PROMENITI PASSENGERA) "TESTIRATI"

    @PostMapping(value = "{rideId}/driver",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('PASSENGER')")
    public ResponseEntity<?> createDriverReview(@PathVariable("rideId") String rideId, @Valid @RequestBody RequestReviewDTO requestReviewDTO){

        String passengerId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId().toString();
        System.out.println(passengerId);


        if(!StringUtils.isNumeric(passengerId)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!this.passengerService.existsById(passengerId)){
            return new ResponseEntity<>(new MessageDTO("PassengerId does not exist!"), HttpStatus.NOT_FOUND);
        }
        if(!StringUtils.isNumeric(rideId)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!this.rideService.existsById(rideId.toString())){
            return new ResponseEntity<>("Ride does not exist!", HttpStatus.NOT_FOUND);
        }
        if(!this.rideService.checkIfPassengerExistInRide(rideId, passengerId)){
            return new ResponseEntity<>(new MessageDTO("Passenger is not in that ride!"), HttpStatus.NOT_FOUND);
        }

        Ride ride = this.rideService.getRide(rideId).get();
        Review review = new Review(requestReviewDTO.getRating(), requestReviewDTO.getComment(), ride, this.passengerService.getPassenger(passengerId).get(), ReviewType.DRIVER);
        this.reviewService.add(review);
        ride.getReviews().add(review);
        this.rideService.add(ride);
        return new ResponseEntity<>(review.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/driver/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'PASSENGER')")
    public ResponseEntity<?> getDriverReviews(@PathVariable("id") String id) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!this.driverService.getDriver(id).isPresent()){
            return new ResponseEntity<>("Driver does not exist!", HttpStatus.NOT_FOUND);
        }

        List<Review> reviewsForDriver = this.reviewService.getReviewsForDriver(id);

        List<ResponseReviewDTO> responseReviewDTOS = new ArrayList<>();
        for(Review r : reviewsForDriver){
            responseReviewDTOS.add(r.parseToResponse());
        }

        return new ResponseEntity<>(new ResponsePageDTO(reviewsForDriver.size(), Arrays.asList(responseReviewDTOS.toArray())), HttpStatus.OK);
    }

    @GetMapping(value = "/{rideId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'PASSENGER')")
    public ResponseEntity<?> getRideReviews(@PathVariable("rideId") String rideId) {

        if(!StringUtils.isNumeric(rideId)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!this.rideService.getRide(rideId).isPresent()){
            return new ResponseEntity<>(new MessageDTO("Ride does not exist!"), HttpStatus.NOT_FOUND);
        }

        List<ResponseRideReviewsDTO> responseRideReviewsDTOS = this.reviewService.getRideReviews(rideId);

        return new ResponseEntity<>(responseRideReviewsDTOS, HttpStatus.OK);
    }

}
