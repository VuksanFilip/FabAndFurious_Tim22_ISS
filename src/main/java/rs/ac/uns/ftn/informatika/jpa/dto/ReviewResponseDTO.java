package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.Passenger;

public class ReviewResponseDTO {

//    {
//        "id": 123,
//            "rating": 3,
//            "comment": "The driver was driving really fast",
//            "passenger": {
//        "id": 123,
//                "email": "user@example.com"
//    }
//    }

    private Long id;
    private int rating;
    private String comment;
    private Passenger passenger;

    public ReviewResponseDTO() {
    }

    public ReviewResponseDTO(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
        this.passenger = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
}
