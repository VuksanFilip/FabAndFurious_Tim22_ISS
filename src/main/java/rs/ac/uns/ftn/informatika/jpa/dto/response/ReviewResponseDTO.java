package rs.ac.uns.ftn.informatika.jpa.dto.response;

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
    private float rating;
    private String comment;
    private Passenger passenger;

    public ReviewResponseDTO() {
    }

    public ReviewResponseDTO(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
        this.passenger = null;
    }

    public ReviewResponseDTO( Long id , float rating, String comment) {
        this.id = id;
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

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
}
