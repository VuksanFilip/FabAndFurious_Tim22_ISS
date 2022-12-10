package rs.ac.uns.ftn.informatika.jpa.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.PassengerResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.ReviewResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.CreateReviewDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.ReviewResponseVehicleDTO;
import rs.ac.uns.ftn.informatika.jpa.dummy.ReviewDummy;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
import rs.ac.uns.ftn.informatika.jpa.model.Review;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private ReviewDummy reviewDummy = new ReviewDummy();

    @PostMapping(value = "{rideId}/vehicle/{vehicleId}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewResponseDTO> createVehicleReview(@PathVariable("rideId") Long rideId,@PathVariable("vehicleId") Long vehicleId,@RequestBody CreateReviewDTO review) throws Exception {
        Long id = reviewDummy.counter.incrementAndGet();
        ReviewResponseDTO reviewResponse = review.parseToResponse(id);
        reviewDummy.reviewsForVehicles.put(id, review.parseToReview(id, rideId, vehicleId));
        return new ResponseEntity<ReviewResponseDTO>(reviewResponse, HttpStatus.CREATED);
    }

//    @PostMapping(value = "{rideId}/driver/{driverId}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ReviewResponseDTO> createDriverReview(@RequestBody CreateReviewDTO review) throws Exception {
//        Long id = reviewDummy.counter.incrementAndGet();
//        ReviewResponseDTO reviewResponse = review.parseToResponse(id);
//        reviewDummy.reviewsForDrivers.put(id, review.parseToReview(id));
//        return new ResponseEntity<ReviewResponseDTO>(reviewResponse, HttpStatus.CREATED);
//    }

    @GetMapping(value = "/vehicle/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewResponseVehicleDTO> getVehicleReviews(@PathVariable("id") Long id) {

        Review review = new Review();
        for (Review r: reviewDummy.reviewsForVehicles.values()){
            if (r.getVehicleId() == id){
                review = r;

            }
        }
        int totalCount = reviewDummy.reviewsForVehicles.size();
        if (review == null) {
            return new ResponseEntity<ReviewResponseVehicleDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ReviewResponseVehicleDTO>(review.parseToResponseVehicle(totalCount, id), HttpStatus.OK);
    }

}
