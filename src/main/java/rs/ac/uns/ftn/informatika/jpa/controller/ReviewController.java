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
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IReviewService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IRideService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IVehicleService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private IRideService rideService;
    private IReviewService reviewService;
    private IVehicleService vehicleService;

    @Autowired
    public ReviewController(IReviewService reviewService, IVehicleService vehicleService, IRideService rideService) {
        this.reviewService = reviewService;
        this.vehicleService = vehicleService;
        this.rideService = rideService;
    }

    @PostMapping(value = "{rideId}/vehicle",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseReviewDTO> createVehicleReview(@PathVariable("rideId") String rideId, @RequestBody RequestReviewDTO requestReviewDTO) throws Exception {
        Ride ride = rideService.getRide(rideId).get();
        Review review = requestReviewDTO.parseToReviewVehicle(ride);
        Vehicle vehicle = vehicleService.getVehicle(ride.getVehicle().getId().toString()).get();
        vehicle.getReviews().add(review);
        reviewService.add(review);

        return new ResponseEntity<>(review.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/vehicle/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePageDTO> getVehicleReviews(@PathVariable("id") String id, Pageable page) {

        Page<Review> reviews = reviewService.findAll(page);
        List<ResponseReviewDTO> responseReviewDTOS = new ArrayList<>();
        for(Review r: reviews){
//            if(r.getVehicleId() == Long.parseLong(id)){
//                responseReviewDTOS.add(r.parseToResponse());
//            }
        }

        List<Review> reviewsForSize = reviewService.getAll();
        int result = 0;
        for(Review r: reviewsForSize){
//            if(r.getVehicleId() == Long.parseLong(id)){
//                result = result + 1;
//            }
        }
        return new ResponseEntity<>(new ResponsePageDTO(result, Arrays.asList(responseReviewDTOS.toArray())), HttpStatus.OK);
    }

    @PostMapping(value = "{rideId}/driver/{driverId}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseReviewDTO> createDriverReview(@PathVariable("rideId") Long rideId, @PathVariable("driverId") Long driverId, @RequestBody RequestReviewDTO requestReviewDTO) throws Exception {
        Review review = requestReviewDTO.parseToReviewDriver(rideId, driverId);
        review.setPassenger(new Passenger());
        reviewService.add(review);

        return new ResponseEntity<>(review.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/driver/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePageDTO> getDriverReviews(@PathVariable("id") String id, Pageable page) {

        Page<Review> reviews = reviewService.findAll(page);
        List<ResponseReviewDTO> responseReviewDTOS = new ArrayList<>();
        for(Review r: reviews){
//            if(r.getDriverId() == Long.parseLong(id)){
//                responseReviewDTOS.add(r.parseToResponse());
//            }
        }

        List<Review> reviewsForSize = reviewService.getAll();
        int result = 0;
        for(Review r: reviewsForSize){
//            if(r.getVehicleId() == Long.parseLong(id)){
//                result = result + 1;
//            }
        }
        return new ResponseEntity<>(new ResponsePageDTO(result, Arrays.asList(responseReviewDTOS.toArray())), HttpStatus.OK);
    }

    //TODO NAPRAVTI POSEBAN VEHICLE I POSEBAN DRIVER REVIEW KOJI NASLEDJUJU REVIEW
//    @GetMapping(value = "/driver/{rideId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ResponsePageDTO> getDriverReviews(@PathVariable("id") String id) {
//
//        List<Review> reviews = reviewService.getAll();
//        List<ResponseReviewDTO> responseReviewDTOS = new ArrayList<>();
//        for(Review r: reviews){
//            if(r.getRide().getId() == Long.parseLong(id)){
//                responseReviewDTOS.add(r.parseToResponse());
//            }
//        }
//
//        return new ResponseEntity<>(new ResponsePageDTO(result, Arrays.asList(responseReviewDTOS.toArray())), HttpStatus.OK);
//    }


}
