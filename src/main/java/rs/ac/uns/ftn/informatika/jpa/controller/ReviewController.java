package rs.ac.uns.ftn.informatika.jpa.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestReviewDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePageDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseReviewDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
import rs.ac.uns.ftn.informatika.jpa.model.Review;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.model.Vehicle;
import rs.ac.uns.ftn.informatika.jpa.model.enums.ReviewType;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private IRideService rideService;
    private IReviewService reviewService;
    private IVehicleService vehicleService;
    private IPassengerService passengerService;
    private IDriverService driverService;


    @Autowired
    public ReviewController(IReviewService reviewService, IVehicleService vehicleService, IRideService rideService, IPassengerService passengerService, IDriverService driverService) {
        this.reviewService = reviewService;
        this.vehicleService = vehicleService;
        this.rideService = rideService;
        this.passengerService = passengerService;
        this.driverService = driverService;
    }

    @PostMapping(value = "{rideId}/vehicle",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createVehicleReview(@PathVariable("rideId") String rideId, @RequestBody RequestReviewDTO requestReviewDTO) {
        if(!this.rideService.existsById(rideId)){
            return new ResponseEntity<>("Ride does not exist!", HttpStatus.NOT_FOUND);
        }
        if(this.rideService.getRide(rideId).get().getVehicle() == null){
            return new ResponseEntity<>("Vehicle does not exist!", HttpStatus.NOT_FOUND);
        }
        Ride ride = this.rideService.getRide(rideId).get();
        Review review = new Review(requestReviewDTO.getRating(), requestReviewDTO.getComment(), ride, this.passengerService.getPassenger("2").get(), ReviewType.VEHICLE);
        this.reviewService.add(review);
        ride.getReviews().add(review);
        this.rideService.add(ride);
        Vehicle vehicle = this.vehicleService.getVehicle(ride.getVehicle().getId().toString()).get();
        vehicle.getReviews().add(review);
        this.vehicleService.add(vehicle);
        return new ResponseEntity<>(review.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/vehicle/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVehicleReviews(@PathVariable("id") String id) {
        if(!this.vehicleService.getVehicle(id).isPresent()){
            return new ResponseEntity<>("Vehicle does not exist", HttpStatus.NOT_FOUND);
        }
        Vehicle vehicle = this.vehicleService.getVehicle(id).get();
        List<ResponseReviewDTO> responseReviewDTOS = new ArrayList<>();
        for(Review r : vehicle.getReviews()){
            responseReviewDTOS.add(r.parseToResponse());
        }
        return new ResponseEntity<>(new ResponsePageDTO(vehicle.getReviews().size(), Arrays.asList(responseReviewDTOS.toArray())), HttpStatus.OK);
    }

    @PostMapping(value = "{rideId}/driver",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDriverReview(@PathVariable("rideId") Long rideId, @RequestBody RequestReviewDTO requestReviewDTO){
        if(!this.rideService.existsById(rideId.toString())){
            return new ResponseEntity<>("Ride does not exist!", HttpStatus.NOT_FOUND);
        }
        Ride ride = this.rideService.getRide(rideId.toString()).get();
        Review review = new Review(requestReviewDTO.getRating(), requestReviewDTO.getComment(), ride, this.passengerService.getPassenger("2").get(), ReviewType.DRIVER);
        this.reviewService.add(review);
        ride.getReviews().add(review);
        this.rideService.add(ride);
        return new ResponseEntity<>(review.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/driver/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDriverReviews(@PathVariable("id") String id) {
        if(!this.driverService.getDriver(id).isPresent()){
            return new ResponseEntity<>("Driver does not exist!", HttpStatus.NOT_FOUND);
        }
        List<Review> allReviews = this.reviewService.getAll();
        List<Review> reviewsForDriver = new ArrayList<>();
        for(Review r : allReviews){
            if(r.getReviewType() == ReviewType.DRIVER && r.getRide().getDriver().getId().toString().equals(id)){
                reviewsForDriver.add(r);
            }
        }
        List<ResponseReviewDTO> responseReviewDTOS = new ArrayList<>();
        for(Review r : reviewsForDriver){
            responseReviewDTOS.add(r.parseToResponse());
        }
        return new ResponseEntity<>(new ResponsePageDTO(reviewsForDriver.size(), Arrays.asList(responseReviewDTOS.toArray())), HttpStatus.OK);
    }

    @GetMapping(value = "/{rideId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRideReviews(@PathVariable("rideId") String rideId) {
        if(!this.rideService.getRide(rideId).isPresent()){
            return new ResponseEntity<>("Ride does not exist!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(this.rideService.getRide(rideId).get().parseToResponseAllReviews(), HttpStatus.OK);
    }

}
