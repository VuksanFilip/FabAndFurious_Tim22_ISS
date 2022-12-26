package rs.ac.uns.ftn.informatika.jpa.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestReviewDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseAllVehicleReviewsDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseReviewDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseReviewDriverDTO;
import rs.ac.uns.ftn.informatika.jpa.dummy.ReviewDummy;
import rs.ac.uns.ftn.informatika.jpa.model.Review;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private ReviewDummy reviewDummy = new ReviewDummy();

    @PostMapping(value = "{rideId}/vehicle/{vehicleId}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseReviewDTO> createVehicleReview(@PathVariable("rideId") Long rideId, @PathVariable("vehicleId") Long vehicleId, @RequestBody RequestReviewDTO review) throws Exception {
        Long id = reviewDummy.counter.incrementAndGet();
        ResponseReviewDTO reviewResponse = review.parseToResponse(id);
        reviewDummy.reviewsForVehicles.put(id, review.parseToReviewVehicle(id, rideId, vehicleId));
        return new ResponseEntity<ResponseReviewDTO>(reviewResponse, HttpStatus.OK);
    }

    @PostMapping(value = "{rideId}/driver/{driverId}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseReviewDTO> createDriverReview(@PathVariable("rideId") Long rideId, @PathVariable("driverId") Long driverId, @RequestBody RequestReviewDTO review) throws Exception {
        Long id = reviewDummy.counter.incrementAndGet();
        ResponseReviewDTO reviewResponse = review.parseToResponse(id);
        reviewDummy.reviewsForDrivers.put(id, review.parseToReviewDriver(id, rideId, driverId));
        return new ResponseEntity<ResponseReviewDTO>(reviewResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/vehicle/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseAllVehicleReviewsDTO> getVehicleReviews(@PathVariable("id") Long id) {
        ArrayList<ResponseReviewDTO> responseReviewDTOS = new ArrayList<>();
        for (Review r: reviewDummy.reviewsForVehicles.values()){
            if (r.getVehicleId() == id){
                responseReviewDTOS.add(r.parseToResponse());
            }
        }
        return new ResponseEntity<ResponseAllVehicleReviewsDTO>(new ResponseAllVehicleReviewsDTO(responseReviewDTOS.size(), responseReviewDTOS), HttpStatus.OK);
    }

    @GetMapping(value = "/driver/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseReviewDriverDTO> getDriverReviews(@PathVariable("id") Long id) {

        Review review = new Review();
        int totalCount = 0;
        for (Review r: reviewDummy.reviewsForDrivers.values()){
            if (r.getDriverId() == id){
                totalCount += 1;
                review = r;

            }
        }
        if (review == null) {
            return new ResponseEntity<ResponseReviewDriverDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ResponseReviewDriverDTO>(review.parseToResponseDriver(totalCount, id), HttpStatus.OK);
    }

}
