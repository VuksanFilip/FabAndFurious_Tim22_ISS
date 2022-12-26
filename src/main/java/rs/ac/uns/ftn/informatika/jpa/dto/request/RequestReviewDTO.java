package rs.ac.uns.ftn.informatika.jpa.dto.request;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseReviewDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Review;

public class RequestReviewDTO {

    //{
//        "rating": 3,
//        "comment": "The vehicle was bad and dirty"
//        }

    private int rating;
    private String comment;

    public RequestReviewDTO() {
    }

    public RequestReviewDTO(int rating, String comment) {
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

    public ResponseReviewDTO parseToResponse(Long id){
        ResponseReviewDTO reviewResponse = new ResponseReviewDTO(this.rating, this.comment);
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
