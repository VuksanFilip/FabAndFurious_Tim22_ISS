package rs.ac.uns.ftn.informatika.jpa.dto.create;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ReviewResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Review;

public class CreateReviewDTO {

    //{
//        "rating": 3,
//        "comment": "The vehicle was bad and dirty"
//        }

    private int rating;
    private String comment;

    public CreateReviewDTO() {
    }

    public CreateReviewDTO(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ReviewResponseDTO parseToResponse(Long id){
        ReviewResponseDTO reviewResponse = new ReviewResponseDTO(this.rating, this.comment);
        reviewResponse.setId(id);
        return reviewResponse;
    }


    public Review parseToReviewVehicle(Long id, Long rideId, Long vehicleId){
        return new Review(id, this.rating, this.comment, null, rideId, vehicleId,null);
    }

    public Review parseToReviewDriver(Long id, Long rideId, Long driverId){
        return new Review(id, this.rating, this.comment, null, rideId, null,driverId);
    }
}
