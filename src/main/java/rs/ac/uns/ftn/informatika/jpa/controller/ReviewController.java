package rs.ac.uns.ftn.informatika.jpa.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.jpa.dto.ReviewResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.CreateReviewDTO;
import rs.ac.uns.ftn.informatika.jpa.dummy.ReviewDummy;
import rs.ac.uns.ftn.informatika.jpa.model.Review;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private ReviewDummy reviewDummy = new ReviewDummy();

    @PostMapping(value = "{rideId}/vehicle/{vehicleId}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewResponseDTO> createVehicleReview(@RequestBody CreateReviewDTO review) throws Exception {
        Long id = reviewDummy.counter.incrementAndGet();
        ReviewResponseDTO reviewResponse = review.parseToResponse(id);
        reviewDummy.reviews.put(id, review.parseToReview(id));
        return new ResponseEntity<ReviewResponseDTO>(reviewResponse, HttpStatus.CREATED);
    }

}
