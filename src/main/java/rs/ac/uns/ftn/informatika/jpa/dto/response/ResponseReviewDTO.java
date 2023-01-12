package rs.ac.uns.ftn.informatika.jpa.dto.response;

public class ResponseReviewDTO {

    private Long id;
    private float rating;
    private String comment;
    private ResponsePassengerIdEmailDTO passenger;

    public ResponseReviewDTO() {
    }

    public ResponseReviewDTO(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
        this.passenger = null;
    }

    public ResponseReviewDTO(Long id , float rating, String comment) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.passenger = null;
    }

    public ResponseReviewDTO(Long id , float rating, String comment, ResponsePassengerIdEmailDTO passenger) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.passenger = passenger;
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

    public ResponsePassengerIdEmailDTO getPassenger() {
        return passenger;
    }

    public void setPassenger(ResponsePassengerIdEmailDTO passenger) {
        this.passenger = passenger;
    }
}
