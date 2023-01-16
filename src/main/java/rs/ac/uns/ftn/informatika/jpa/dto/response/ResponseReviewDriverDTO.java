package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.model.Passenger;

public class ResponseReviewDriverDTO {

    private Long id;

    private float rating;

    private String comment;

    private ResponseReviewPassengerDTO passenger;

    public ResponseReviewDriverDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ResponseReviewPassengerDTO getPassenger() {
        return passenger;
    }

    public void setPassenger(ResponseReviewPassengerDTO passenger) {
        this.passenger = passenger;
    }
}
